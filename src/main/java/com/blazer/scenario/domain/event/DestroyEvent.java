package com.blazer.scenario.domain.event;

import com.blazer.scenario.domain.HangupType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class DestroyEvent extends AbstractEvent {
    protected HangupType hangup;

    public DestroyEvent(Long id, Long chain, Date date, HangupType hangup) {
        this.id = id;
        this.chain = chain;
        this.date = date;
        this.hangup = hangup;
    }
}
