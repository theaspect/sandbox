package com.blazer.scenario.domain.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class BridgeEvent extends AbstractEvent {
    protected Long legB;

    public BridgeEvent(Long id, Long chain, Date date, Long legB) {
        this.id = id;
        this.chain = chain;
        this.date = date;
        this.legB = legB;
    }
}
