package com.blazer.scenario.event;

import com.blazer.scenario.domain.Service;
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

    public BridgeEvent(Long id, Long chain, Date date, Service service, String name, Long legB) {
        fillAbstract(id, chain, date, service, name);
        this.legB = legB;
    }
}
