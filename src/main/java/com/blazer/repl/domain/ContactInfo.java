package com.blazer.repl.domain;

import lombok.Getter;

import javax.persistence.*;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "contact_info")
@Getter
public class ContactInfo {
    @Id
    @GeneratedValue
    Long id;

    @Column(name = "value")
    String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "info_type")
    InfoType infoType;

    public static enum InfoType{
        HOME, OFFICE, MOBILE
    }
}
