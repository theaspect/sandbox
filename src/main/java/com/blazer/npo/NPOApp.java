package com.blazer.npo;

import com.blazer.npo.domain.*;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Random;
import java.util.UUID;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Slf4j
public class NPOApp {
    Provider<EntityManager> entityManagerProvider;
    Provider<Dao> daoProvider;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new NPOModel());

        NPOApp app = injector.getInstance(NPOApp.class);

        app.init();
        app.lazyLoad();
        app.cacheWarmUpFetch();
        app.cacheWarmUp();
        app.cacheWarmUpNested();

        injector.getInstance(EntityManagerFactory.class).close();
    }

    @SuppressWarnings("unused")
    @Inject
    private NPOApp(Provider<EntityManager> entityManagerProvider, Provider<Dao> daoProvider) {
        this.entityManagerProvider = entityManagerProvider;
        this.daoProvider = daoProvider;
    }

    private void lazyLoad() {
        log.debug("*** Show lazy loading");
        Dao dao = daoProvider.get();
        long count = 0;
        try {
            count = dao.getStatistics().getPrepareStatementCount();
            log.debug("Iterate");
            for (Parent p : dao.parentsFindAll()) {
                log.trace(p.toString());
            }
            count = dao.getStatistics().getPrepareStatementCount() - count;
        } finally {
            NPOApp.log.debug("Total queries {}", count);
            dao.getEntityManager().close();
        }
    }

    private void cacheWarmUpFetch() {
        log.debug("*** Cache warm up fetch");
        Dao dao = daoProvider.get();
        long count = 0;
        try {
            count = dao.getStatistics().getPrepareStatementCount();
            dao.parentsJoinMtM();
            log.debug("Get P + MtM {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.childMtMJoinOtMNested();
            log.debug("Get MtM + OtMNested {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.parentsJoinOtMFetch();
            log.debug("Get P + OtM Fetch {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.childOtMJoinOtO();
            log.debug("Get OtM + OtO {}", dao.getStatistics().getPrepareStatementCount() - count);
            log.debug("Iterate");
            for (Parent p : dao.parentsFindAll()) {
                log.trace(p.toString());
            }
            count = dao.getStatistics().getPrepareStatementCount() - count;
        } finally {
            NPOApp.log.debug("Total queries {}", count);
            dao.getEntityManager().close();
        }
    }

    private void cacheWarmUp() {
        log.debug("*** Cache warm up");
        Dao dao = daoProvider.get();
        long count = 0;
        try {
            count = dao.getStatistics().getPrepareStatementCount();
            dao.parentsJoinMtM();
            log.debug("Get P + MtM {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.childMtMJoinOtMNested();
            log.debug("Get MtM + OtMNested {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.parentsJoinOtM();
            log.debug("Get P + OtM {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.childOtMJoinOtO();
            log.debug("Get OtM + OtO {}", dao.getStatistics().getPrepareStatementCount() - count);
            log.debug("Iterate");
            for (Parent p : dao.parentsFindAll()) {
                log.trace(p.toString());
            }
            count = dao.getStatistics().getPrepareStatementCount() - count;
        } finally {
            NPOApp.log.debug("Total queries {}", count);
            dao.getEntityManager().close();
        }
    }

    private void cacheWarmUpNested() {
        log.debug("*** Cache warm up nested");
        Dao dao = daoProvider.get();
        long count = 0;
        try {
            count = dao.getStatistics().getPrepareStatementCount();
            dao.parentsJoinMtM();
            log.debug("Get P + MtM {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.childMtMJoinOtMNested();
            log.debug("Get MtM + OtMNested {}", dao.getStatistics().getPrepareStatementCount() - count);
            dao.parentsOtMJoinOtMtO();
            log.debug("Get P + OtM + OtO {}", dao.getStatistics().getPrepareStatementCount() - count);
            log.debug("Iterate");
            for (Parent p : dao.parentsFindAll()) {
                log.trace(p.toString());
            }
            count = dao.getStatistics().getPrepareStatementCount() - count;
        } finally {
            NPOApp.log.debug("Total queries {}", count);
            dao.getEntityManager().close();
        }
    }

    private void init() {
        log.debug("*** Init test data");
        EntityManager em = entityManagerProvider.get();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Random random = new Random();
            ChildMtM[] childMtMs = new ChildMtM[100];
            for (int i = 0; i < childMtMs.length; i++) {
                childMtMs[i] = new ChildMtM();
                childMtMs[i].setValue(UUID.randomUUID().toString());
                em.persist(childMtMs[i]);
            }

            ChildOtMNested[] childOtMNesteds = new ChildOtMNested[100];
            for (int i = 0; i < childOtMNesteds.length; i++) {
                childOtMNesteds[i] = new ChildOtMNested();
                childOtMNesteds[i].setValue(UUID.randomUUID().toString());
                childOtMNesteds[i].setParent(childMtMs[random.nextInt(childMtMs.length)]);

                em.persist(childOtMNesteds[i]);
            }

            Parent[] parents = new Parent[10];
            for (int i = 0; i < parents.length; i++) {
                parents[i] = new Parent();
                for (int j = 0; j < 10; j++) {
                    parents[i].getChildMtM().add(childMtMs[random.nextInt(childMtMs.length)]);
                }
                em.persist(parents[i]);
            }

            ChildOtM[] childOtMs = new ChildOtM[100];
            for (int i = 0; i < childOtMs.length; i++) {
                childOtMs[i] = new ChildOtM();
                childOtMs[i].setValue(UUID.randomUUID().toString());
                childOtMs[i].setParent(parents[random.nextInt(parents.length)]);

                ChildOtO childOtO = new ChildOtO();
                childOtO.setValue(UUID.randomUUID().toString());

                childOtMs[i].setNested(childOtO);
                childOtO.setParent(childOtMs[i]);

                em.persist(childOtMs[i]);
            }

            tx.commit();
        } catch (Exception e) {
            log.error("Cannot init database", e);
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
