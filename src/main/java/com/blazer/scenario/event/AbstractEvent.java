package com.blazer.scenario.event;

import com.blazer.scenario.domain.Service;
import lombok.Getter;
import lombok.Setter;

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
    protected Long date;
    protected Service service;
    protected String name;

    protected void fillAbstract(Long id, Long chain, Long date, Service service, String name){
        this.id = id;
        this.chain = chain;
        this.date = date;
        this.service = service;
        this.name = name;
    }
}
