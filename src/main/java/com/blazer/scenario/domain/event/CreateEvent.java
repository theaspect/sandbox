package com.blazer.scenario.domain.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class CreateEvent extends AbstractEvent {
    public CreateEvent(Long id, Long chain, Date date) {
        this.id = id;
        this.chain = chain;
        this.date = date;
    }
}
