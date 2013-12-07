package com.blazer.paging;

import com.blazer.paging.domain.ChildFilter;
import com.blazer.paging.domain.ParentFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.ProfilerRegistry;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
public class PagingApp {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("NPO");
    private static final Random random = new Random(0L);
    private static final Profiler profiler = new Profiler("Paging");
    public static final int SAMPLE_SIZE = 100000;

    public static void main(String[] args) {
        PagingApp app = new PagingApp();

        ProfilerRegistry profilerRegistry = ProfilerRegistry.getThreadContextInstance();
        profiler.registerWith(profilerRegistry);

        // After init you can safely enable SQL output
        profiler.start("init");
        Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
        Logger.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.OFF);
        app.init();
        Logger.getLogger("org.hibernate.SQL").setLevel(Level.DEBUG);
        Logger.getLogger("org.hibernate.type.descriptor.sql.BasicBinder").setLevel(Level.TRACE);

        log.debug("Without limit just FYI");

        profiler.start("LJ+Order");
        app.leftJoinOrder(false);

        profiler.start("LJ+Distinct+Order");
        app.leftJoinDistinctOrder(false);

        log.debug("Fetch with Limit");

        // HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
        profiler.start("Fetch+Limit");
        app.fetchLimit(true);

        log.debug("With limit to stem ORMing");

        profiler.start("LJ+Distinct+Order+Lim");
        app.leftJoinDistinctOrder(true);

        profiler.start("Filter+Lim");
        app.Filter(true);

        profiler.start("FilterBoth+Lim");
        app.FilterBoth(true);

        profiler.startNested("FilterBothSmart");
        app.FilterBothSmart(true);

        profiler.stop().print();
        emf.close();
    }

    private void leftJoinOrder(boolean limit) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<ParentFilter> query = em.createQuery(
                "SELECT pf FROM ParentFilter pf LEFT JOIN pf.child cf ORDER BY pf.value",
                ParentFilter.class);
        if (limit) {
            query.setMaxResults(10);
        }
        List<ParentFilter> result = query.getResultList();
        log.debug("Total {} rows", result.size());

        PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount());
        em.close();
    }

    private void leftJoinDistinctOrder(boolean limit) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<ParentFilter> query = em.createQuery(
                "SELECT DISTINCT pf FROM ParentFilter pf LEFT JOIN pf.child cf ORDER BY pf.value",
                ParentFilter.class);
        if (limit) {
            query.setMaxResults(10);
        }
        List<ParentFilter> result = query.getResultList();
        log.debug("Total {} rows", result.size());

        PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount());
        em.close();
    }

    private void fetchLimit(boolean limit) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<ParentFilter> query = em.createQuery(
                "SELECT DISTINCT pf FROM ParentFilter pf LEFT JOIN FETCH pf.child cf ORDER BY pf.value",
                ParentFilter.class);
        if (limit) {
            query.setMaxResults(10);
        }
        List<ParentFilter> result = query.getResultList();
        log.debug("Total {} rows", result.size());

        PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount());
        em.close();
    }

    private void Filter(boolean limit) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<ParentFilter> query = em.createQuery(
                "SELECT DISTINCT pf FROM ParentFilter pf LEFT JOIN pf.child cf WHERE pf.value BETWEEN :p1 AND :p2 ORDER BY pf.value",
                ParentFilter.class);
        if (limit) {
            query.setMaxResults(10);
        }
        query.setParameter("p1", Integer.MIN_VALUE);
        query.setParameter("p2", Integer.MAX_VALUE);
        List<ParentFilter> result = query.getResultList();
        log.debug("Total {} rows", result.size());

        PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount());
        em.close();
    }

    private void FilterBoth(boolean limit) {
        EntityManager em = emf.createEntityManager();

        TypedQuery<ParentFilter> query = em.createQuery(
                "SELECT DISTINCT pf FROM ParentFilter pf LEFT JOIN pf.child cf " +
                        "WHERE pf.value BETWEEN :p1 AND :p2 " +
                        "AND cf.value BETWEEN :p3 AND :p4 " +
                        "ORDER BY pf.value",
                ParentFilter.class);
        if (limit) {
            query.setMaxResults(10);
        }
        query.setParameter("p1", Integer.MIN_VALUE);
        query.setParameter("p2", Integer.MAX_VALUE);
        query.setParameter("p3", Integer.MIN_VALUE);
        query.setParameter("p4", Integer.MAX_VALUE);
        List<ParentFilter> result = query.getResultList();
        log.debug("Total {} rows", result.size());

        PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount());
        em.close();
    }

    private void FilterBothSmart(boolean limit) {
        EntityManager em = emf.createEntityManager();

        ProfilerRegistry profilerRegistry = ProfilerRegistry.getThreadContextInstance();
        Profiler profiler = profilerRegistry.get("FilterBothSmart");

        profiler.start("Prefetch");
        // Stupid H2DB cannot ORDER BY field is not in the result list
        TypedQuery<Object[]> prefetch = em.createQuery(
                "SELECT pf.id, pf.value FROM ParentFilter pf " +
                        "WHERE pf.value BETWEEN :p1 AND :p2 " +
                        "ORDER BY pf.value",
                Object[].class);

        if (limit) {
            prefetch.setMaxResults(10);
        }
        prefetch.setParameter("p1", Integer.MIN_VALUE);
        prefetch.setParameter("p2", Integer.MAX_VALUE);
        List<Long> ids = new LinkedList<>();
        for (Object[] r : prefetch.getResultList()) {
            ids.add((Long) r[0]);
        }
        log.debug("Prefetch {} rows", ids.size());

        profiler.start("Fetch");
        TypedQuery<ParentFilter> query = em.createQuery(
                "SELECT DISTINCT pf FROM ParentFilter pf LEFT JOIN pf.child cf " +
                        "WHERE pf.id in (:p1) " +
                        "AND cf.value BETWEEN :p3 AND :p4 " +
                        "ORDER BY pf.value",
                ParentFilter.class);
        // We don't have to limit here
        // if (limit) {
        //     query.setMaxResults(10);
        // }
        query.setParameter("p1", ids);
        query.setParameter("p3", Integer.MIN_VALUE);
        query.setParameter("p4", Integer.MAX_VALUE);
        List<ParentFilter> result = query.getResultList();
        log.debug("Total {} rows", result.size());

        PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount());
        em.close();
    }

    private void init() {
        PagingApp.log.debug("*** Init test data");
        EntityManager em = emf.createEntityManager();
        long count = getStatistics(em).getPrepareStatementCount();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            for (int i = 0; i < SAMPLE_SIZE; i++) {
                ParentFilter pf = new ParentFilter();
                pf.setValue(random.nextInt());
                pf.setNonIndex(random.nextInt());
                for (int j = 0; j < random.nextInt(10); j++) {
                    ChildFilter cf = new ChildFilter();
                    cf.setParent(pf);
                    cf.setValue(random.nextInt());
                    pf.getChild().add(cf);
                }
                em.persist(pf);
            }

            tx.commit();
        } catch (Exception e) {
            PagingApp.log.error("Cannot init database", e);
            tx.rollback();
        } finally {
            PagingApp.log.debug("Total {} DML", getStatistics(em).getPrepareStatementCount() - count);
            em.close();
        }
    }

    public Statistics getStatistics(EntityManager entityManager) {
        return ((Session) entityManager.getDelegate()).getSessionFactory().getStatistics();
    }
}
