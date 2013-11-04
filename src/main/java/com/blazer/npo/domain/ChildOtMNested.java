package com.blazer.npo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "child_otm_nested")
@Getter
@Setter
@ToString(of = {"id"})
public class ChildOtMNested {
    @Id
    @GeneratedValue
    Long id;

    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    ChildMtM parent;
}
