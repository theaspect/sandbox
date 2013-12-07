package com.blazer.paging.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
@Entity
@Table(name = "child_filter")
@Getter
@Setter
@ToString(of = {"id", "value"})
public class ChildFilter {
    @Id
    @GeneratedValue
    Long id;

    // @Index(name = "child_filter_value_idx")
    @Column
    Integer value;

    @ManyToOne
    ParentFilter parent;
}
