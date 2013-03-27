package com.blazer.scenario.call;

import com.blazer.scenario.AbstractDaemon;
import com.blazer.scenario.domain.HangupType;
import com.blazer.scenario.domain.Service;
import com.blazer.scenario.domain.dao.UserDao;
import com.blazer.scenario.event.AbstractEvent;
import com.blazer.scenario.event.BridgeEvent;
import com.blazer.scenario.event.CreateEvent;
import com.blazer.scenario.event.DestroyEvent;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.HashMap;

/**
 * CallCollectorDaemon listen for external events
 * <p/>
 * This class is only for demonstration of first layer
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Log4j
public class CallCollectorDaemon extends AbstractDaemon {
    private CallRouter router;
    private EntityManagerFactory emf;

    public CallCollectorDaemon(CallRouter router) {
        this.emf = null; // In real world here will be connection to datasource
        this.router = router;
        subscribe("CREATE", "BRIDGE", "DESTROY");
    }

    @Override
    public void onEvent(HashMap<String, String> rawEvent) {
        AbstractEvent abstractEvent;

        try{
            // Parse event
            if ("CREATE".equalsIgnoreCase(getType(rawEvent))) {
                router.addEvent(new CreateEvent(
                        getId(rawEvent), getChain(rawEvent),
                        getDate(rawEvent), getService(rawEvent), getNumber(rawEvent)));
            } else if ("BRIDGE".equalsIgnoreCase(getType(rawEvent))) {
                router.addEvent(new BridgeEvent(
                        getId(rawEvent), getChain(rawEvent),
                        getDate(rawEvent), getService(rawEvent), getNumber(rawEvent),
                        getLegB(rawEvent)));
            } else if ("DESTROY".equalsIgnoreCase(getType(rawEvent))) {
                router.addEvent(new DestroyEvent(
                        getId(rawEvent), getChain(rawEvent),
                        getDate(rawEvent), getService(rawEvent), getNumber(rawEvent),
                        getHangupType(rawEvent)));
            } else {
                CallCollectorDaemon.log.error("Unknown event: " + getType(rawEvent));
                return;
            }

            // On router polling multiple records can be produced
            if (router.hasNewRecord()) {
                saveRecord();
            }
        }catch (Exception e){
            CallCollectorDaemon.log.error("Error in daemon", e);
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
            CallCollectorDaemon.log.error("Cannot persist CDR", e);
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

    private Service getService(HashMap<String, String> rawEvent) {
        return Service.valueOf(rawEvent.get("service"));
    }

    private String getNumber(HashMap<String, String> rawEvent) {
        return rawEvent.get("number");
    }

    private Long getLegB(HashMap<String, String> rawEvent) {
        return Long.valueOf(rawEvent.get("legb"));
    }

    private HangupType getHangupType(HashMap<String, String> rawEvent) {
        return HangupType.valueOf(rawEvent.get("hangup"));
    }

}
