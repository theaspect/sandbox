package com.blazer.paging.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@Entity
@Table(name = "parent_filter")
@Getter
@Setter
@ToString(of = {"id", "value", "child"})
public class ParentFilter {
    @Id
    @GeneratedValue
    Long id;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ChildFilter> child = new LinkedList<>();

    @Index(name = "parent_filter_value_idx")
    @Column
    Integer value;

    // Additional field to suppress index-only scan
    @Column
    Integer nonIndex;
}
