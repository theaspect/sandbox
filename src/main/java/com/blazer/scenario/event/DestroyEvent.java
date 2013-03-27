package com.blazer.scenario.event;

import com.blazer.scenario.domain.HangupType;
import com.blazer.scenario.domain.Service;
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

    public DestroyEvent(Long id, Long chain, Date date, Service service, String name, HangupType hangup) {
        fillAbstract(id, chain, date, service, name);
        this.hangup = hangup;
    }
}
