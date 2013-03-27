package com.blazer.scenario.event;

import com.blazer.scenario.domain.Service;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class CreateEvent extends AbstractEvent {
    public CreateEvent() {
        // For debug purposes
    }

    public CreateEvent(Long id, Long chain, Long date, Service service, String name) {
        fillAbstract(id, chain, date, service, name);
    }
}
