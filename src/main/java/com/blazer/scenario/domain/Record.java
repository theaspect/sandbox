package com.blazer.scenario.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
@Entity
public class Record {
    private Long id;

    private Long chain;

    private Long begin;
    private Long end;

    private User user;
    private String number;

    private Long legB;
    private HangupType hangup;
}
