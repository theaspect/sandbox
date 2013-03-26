package com.blazer.scenario;

import com.blazer.scenario.domain.HangupType;
import com.blazer.scenario.domain.dao.UserDao;
import com.blazer.scenario.domain.event.AbstractEvent;
import com.blazer.scenario.domain.event.BridgeEvent;
import com.blazer.scenario.domain.event.CreateEvent;
import com.blazer.scenario.domain.event.DestroyEvent;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.HashMap;

/**
 * CollectorDaemon listen for external events
 * <p/>
 * This class is only for demonstration of first layer
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Log4j
public class CollectorDaemon extends AbstractDaemon {
    private Router router;
    private EntityManagerFactory emf;

    public CollectorDaemon() {
        emf = null; // In real world here will be connection to datasource
        router = new Router();
        subscribe("CREATE", "BRIDGE", "DESTROY");
    }

    @Override
    public void onEvent(HashMap<String, String> rawEvent) {
        AbstractEvent abstractEvent;

        // Parse event
        if ("CREATE".equalsIgnoreCase(getType(rawEvent))) {
            abstractEvent = new CreateEvent(
                    getId(rawEvent), getChain(rawEvent), getDate(rawEvent));
        } else if ("BRIDGE".equalsIgnoreCase(getType(rawEvent))) {
            abstractEvent = new BridgeEvent(
                    getId(rawEvent), getChain(rawEvent), getDate(rawEvent),
                    getLegB(rawEvent));
        } else if ("DESTROY".equalsIgnoreCase(getType(rawEvent))) {
            abstractEvent = new DestroyEvent(
                    getId(rawEvent), getChain(rawEvent), getDate(rawEvent),
                    getHangupType(rawEvent));
        } else {
            CollectorDaemon.log.error("Unknown event: " + getType(rawEvent));
            return;
        }

        // Send parsed event to router
        router.addEvent(abstractEvent);

        // On router polling multiple records can be produced
        if (router.hasNewRecord()) {
            saveRecord();
        }
    }

    /**
     * Persist records to DB
     */
    private void saveRecord() {
        // Here we can use DI
        EntityManager em = emf.createEntityManager();
        UserDao userDao = new UserDao(em);
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            while (router.hasNewRecord()) {
                em.persist(router.pullRecord(userDao));
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error("Cannot persist CDR", e);
        } finally {
            em.close();
        }
    }

    // Parse raw attributes

    private String getType(HashMap<String, String> rawEvent) {
        return rawEvent.get("type");
    }

    private Long getId(HashMap<String, String> rawEvent) {
        return Long.valueOf(rawEvent.get("id"));
    }

    private Long getChain(HashMap<String, String> rawEvent) {
        return Long.valueOf(rawEvent.get("chain"));
    }

    private Date getDate(HashMap<String, String> rawEvent) {
        return new Date(Long.valueOf(rawEvent.get("date")));
    }

    private Long getLegB(HashMap<String, String> rawEvent) {
        return Long.valueOf(rawEvent.get("legb"));
    }

    private HangupType getHangupType(HashMap<String, String> rawEvent) {
        return HangupType.valueOf(rawEvent.get("hangup"));
    }

}
