package com.blazer.scenario.conference;

import com.blazer.scenario.AbstractDaemon;
import com.blazer.scenario.event.AbstractEvent;
import lombok.extern.log4j.Log4j;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;

/**
 * Stab ConferenceCollectorDaemon listen for external events
 * <p/>
 * This class is only for demonstration of first layer
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Log4j
public class ConferenceCollectorDaemon extends AbstractDaemon {
    private ConferenceRouter router;
    private EntityManagerFactory emf;

    public ConferenceCollectorDaemon(ConferenceRouter router) {
        emf = null; // In real world here will be connection to datasource
        this.router = router;
        subscribe("CREATE_CONFERENCE", "ADD_MEMBER", "DEL_MEMBER");
    }

    @Override
    public void onEvent(HashMap<String, String> rawEvent) {
        AbstractEvent abstractEvent;

        // Parse event

        // Here parsing raw events
        // ...
        abstractEvent = null;
        // ...
        // more parsing


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
        // Saving record to DB
    }

    // Parse raw attributes
    // ...
    //
}
