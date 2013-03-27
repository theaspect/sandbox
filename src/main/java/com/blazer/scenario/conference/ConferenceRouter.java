package com.blazer.scenario.conference;

import com.blazer.scenario.domain.Record;
import com.blazer.scenario.domain.dao.UserDao;
import com.blazer.scenario.event.AbstractEvent;

/**
 * Stab class for routing events to conferences
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class ConferenceRouter {

    public void addEvent(AbstractEvent abstractEvent) {
        // TODO
    }

    public boolean hasNewRecord() {
        // just for demonstration
        return false;
    }

    /**
     * Update single record with info from database
     *
     * @param userDao
     * @return
     */
    public Record pullRecord(UserDao userDao) {
        // just for demonstration
        return null;
    }
}
