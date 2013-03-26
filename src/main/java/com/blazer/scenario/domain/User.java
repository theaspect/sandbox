package com.blazer.scenario.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Entity
@Getter
@Setter
public class User {
    Long id;
    String name;
    String terminal;
}
