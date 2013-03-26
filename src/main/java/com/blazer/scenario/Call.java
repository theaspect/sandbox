package com.blazer.scenario;

import com.blazer.scenario.domain.event.AbstractEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class Call {
    private Long legA;
    private Long legB;

    public boolean hasId(Long id) {
        return id.equals(legA) || id.equals(legB);
    }

    public void addEvent(AbstractEvent event) {
        // TODO
    }
}
