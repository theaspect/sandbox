package com.blazer.npo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 *
 *         http://stackoverflow.com/questions/3537563/1n-selects-with-onetoone-associations
 */
@Entity
@Table(name = "child_otm")
@Getter
@Setter
@ToString(of = {"id", "nested"})
public class ChildOtM {
    @Id
    @GeneratedValue
    Long id;

    String value;

    @ManyToOne
    Parent parent;

    @OneToOne(orphanRemoval = true,
            mappedBy = "parent", optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    /*@JoinColumn(name = "nested_id")*/
    ChildOtO nested;
}
