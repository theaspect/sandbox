package com.blazer.npo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "parent")
@Getter
@Setter
@ToString(of = {"id", "childMtM", "childOtM"})
public class Parent {
    @Id
    @GeneratedValue
    Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "parent_child", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns = @JoinColumn(name = "child_id"))
    List<ChildMtM> childMtM = new LinkedList<>();

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    List<ChildOtM> childOtM = new LinkedList<>();
}
