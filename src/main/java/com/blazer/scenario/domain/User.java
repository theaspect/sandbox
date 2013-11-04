package com.blazer.scenario.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Getter
@Setter
public class User {
    @Id
    Long id;
    String name;
    String terminal;
}
