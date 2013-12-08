package com.blazer.repl.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "contact_info")
@Getter
public class Contact {
    @GeneratedValue
    @Id
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "first_name")
    String lastName;

    List<String> numbers;
}
