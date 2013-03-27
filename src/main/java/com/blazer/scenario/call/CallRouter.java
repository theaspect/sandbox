package com.blazer.scenario.call;

import com.blazer.scenario.domain.Record;
import com.blazer.scenario.domain.Service;
import com.blazer.scenario.domain.User;
import com.blazer.scenario.domain.dao.UserDao;
import com.blazer.scenario.event.BridgeEvent;
import com.blazer.scenario.event.CreateEvent;
import com.blazer.scenario.event.DestroyEvent;
import lombok.extern.log4j.Log4j;

import java.util.LinkedList;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Log4j
public class CallRouter {
    private LinkedList<Call> calls = new LinkedList<Call>();
    private LinkedList<Record> ready = new LinkedList<Record>();

    public void addEvent(CreateEvent event) {
        // Ignore events for ConferenceRouter
        if(event.getService() == Service.CONFERENCE){
            return;
        }

        // Register new call
        Call call = new Call();
        calls.add(call);
        call.addEvent(event);
    }

    public void addEvent(BridgeEvent event) {
        Call call = findCallById(event.getId());

        if (null == call) {
            throw new RuntimeException("Cannot find call id in: \n" + calls.toString());
        }

        // Merge with other leg if exists
        Call callB = findCallById(event.getLegB());
        if(callB == null){
            call.addEvent(event);
        }else{
            call.mergeCalls(event, callB);
        }
    }

    public void addEvent(DestroyEvent event) {
        // We dont care about conference events
        if(event.getService() == Service.CONFERENCE){
            ready.remove(findCallById(event.getId()));
        }

        Call call = findCallById(event.getId());
        if (null == call) {
            log.debug("Hangup unknown channel " + event.getId());
            return;
        }
        call.addEvent(event);

        testCallReady(call);
    }

    private void testCallReady(Call call) {
        // Check for ready record
        if (call.getReady().size() > 0) {
            ready.addAll(call.getReady());
            call.getReady().clear();
        }

        // Remove empty calls
        if (!call.hasLegs()) {
            calls.remove(call);
        }
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
        Record record = ready.pollFirst();

        User user = userDao.findUserByName(record.getNumber());
        if(user == null){
            user = userDao.findUserByTerminal(record.getNumber());
        }

        if(user != null){
            record.setUser(user);
        }

        return record;
    }
}
