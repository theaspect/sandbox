package com.blazer.scenario.call;

import com.blazer.scenario.domain.HangupType;
import com.blazer.scenario.domain.Record;
import com.blazer.scenario.domain.Service;
import com.blazer.scenario.event.AbstractEvent;
import com.blazer.scenario.event.BridgeEvent;
import com.blazer.scenario.event.CreateEvent;
import com.blazer.scenario.event.DestroyEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
@Slf4j
public class Call {
    Long id;
    Set<Long> legs = new HashSet<Long>();
    Record record = null;
    List<Record> ready = new LinkedList<Record>();

    public boolean hasId(Long id) {
        return legs.contains(id);
    }

    public void addEvent(CreateEvent event) {
        if (null != record) {
            record.setEnd(event.getDate());
            if (null == record.getHangup()) {
                record.setHangup(HangupType.REDIRECT);
            }
            ready.add(record);

            //Cleanup legs
            legs.clear();
            legs.add(event.getId());

            commonProcessing (event);
        }else {
            legs.add(event.getId());
        }
    }

    public void addEvent(BridgeEvent event) {
        legs.add(event.getId());

        // Special case for QUEUE
        if(event.getService() == Service.QUEUE){
            record.setLegB(event.getLegB());
        }

        commonProcessing(event);
    }

    public void addEvent(DestroyEvent event) {
        if(record != null){
            record.setEnd(event.getDate());

            // Special case
            if(event.getService() == Service.USER){
                event.setChain(event.getChain());
            }

            ready.add(record);
        }
        commonProcessing(event);
    }

    public void commonProcessing(AbstractEvent event){
        record = new Record();
        record.setId(event.getId());
        record.setChain(event.getChain());
        record.setNumber(event.getName());

        // More processing and cases
        // ...
        //
    }

    public void mergeCalls(BridgeEvent event, Call call) {
        if (null != call.getRecord()) {
            log.debug("Merging");
            // Move all legs from second call
            this.legs.addAll(call.getLegs());
            call.getLegs().clear();

            // Merging record from original leg

            call.getRecord().setNumber(record.getNumber());
            call.getRecord().setBegin(event.getDate());

            record = call.getRecord();
            call.setRecord(null);
        } else {
            log.debug("Swapping");
            // Swap legs between this and call
            // ...
            //
        }
    }

    public boolean hasLegs() {
        return legs.size() > 0;
    }
}
