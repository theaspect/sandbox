package com.blazer.scenario;

import com.blazer.scenario.domain.Record;
import com.blazer.scenario.domain.dao.UserDao;
import com.blazer.scenario.domain.event.AbstractEvent;

import java.util.LinkedList;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class Router {
    LinkedList<Call> calls = new LinkedList<Call>();
    LinkedList<Record> ready = new LinkedList<Record>();

    public void addEvent(AbstractEvent abstractEvent) {
        // TODO
    }

    /**
     * Try to lookup existing call
     *
     * @param id
     * @return
     */
    private Call findCallById(Long id) {
        // Dont care
        if (id == null) {
            return null;
        }

        for (Call c : calls) {
            if (c.hasId(id)) {
                return c;
            }
        }

        return null;
    }

    public boolean hasNewRecord() {
        return ready.size() > 0;
    }

    /**
     * Update single record with info from database
     *
     * @param userDao
     * @return
     */
    public Record pullRecord(UserDao userDao) {
        // TODO
        return null;
    }
}
