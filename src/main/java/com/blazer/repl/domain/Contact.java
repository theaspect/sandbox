package com.blazer.repl.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "contact")
@Getter
@ToString(of = {"firstName", "lastName", "info"})
public class Contact {
    @GeneratedValue
    @Id
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ContactInfo> info = new LinkedList<>();

    public Contact() {
        // Default constructor
    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
