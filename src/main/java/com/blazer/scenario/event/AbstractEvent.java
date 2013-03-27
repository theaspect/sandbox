package com.blazer.scenario.event;

import com.blazer.scenario.domain.Service;
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
    protected Service service;
    protected String name;

    protected void fillAbstract(Long id, Long chain, Date date, Service service, String name){
        this.id = id;
        this.chain = chain;
        this.date = date;
        this.service = service;
        this.name = name;
    }
}
