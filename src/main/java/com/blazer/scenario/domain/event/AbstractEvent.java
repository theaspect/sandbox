package com.blazer.scenario.domain.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Parsed from raw event
 *
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
public class AbstractEvent {
    protected Long id;
    protected Long chain;
    protected Date date;
}
