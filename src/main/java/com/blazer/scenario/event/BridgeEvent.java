package com.blazer.scenario.event;

import com.blazer.scenario.domain.Service;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class BridgeEvent extends AbstractEvent {
    protected Long legB;

    public BridgeEvent() {
        // For debug purposes
    }

    public BridgeEvent(Long id, Long chain, Long date, Service service, String name, Long legB) {
        fillAbstract(id, chain, date, service, name);
        this.legB = legB;
    }
}
