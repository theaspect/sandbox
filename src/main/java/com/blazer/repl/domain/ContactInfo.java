package com.blazer.repl.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "contact_info")
@Getter
@ToString(of = {"infoType", "value"})
public class ContactInfo {
    @Id
    @GeneratedValue
    Long id;

    @Column(name = "value")
    String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "info_type")
    InfoType infoType;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @GsonExclude
    Contact contact;

    public ContactInfo() {
    }

    public ContactInfo(Contact contact, InfoType infoType, String value) {
        this.contact = contact;
        this.infoType = infoType;
        this.value = value;
    }

    public static enum InfoType {
        HOME, OFFICE, MOBILE
    }
}
