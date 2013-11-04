package com.blazer.npo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "child_mtm")
@Getter
@Setter
@ToString(of = {"id", "nested"})
public class ChildMtM {
    @Id
    @GeneratedValue
    Long id;

    String value;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    List<ChildOtMNested> nested = new LinkedList<>();
}
