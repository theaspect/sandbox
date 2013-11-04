package com.blazer.scenario.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Getter
@Setter
@Entity
public class Record {
    @Id
    private Long id;

    private Long chain;

    private Long begin;
    private Long end;

    @ManyToOne
    private User user;
    private String number;

    private Long legB;
    private HangupType hangup;
}
