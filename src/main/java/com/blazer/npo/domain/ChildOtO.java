package com.blazer.npo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 *
 *         http://stackoverflow.com/questions/7025583/hibernate-onetoone-automatic-join-fetching-resolving-n1-problem
 *         http://stackoverflow.com/questions/1444227/making-a-onetoone-relation-lazy
 */
@Entity
@Table(name = "child_oto")
@Getter
@Setter
@ToString(of = {"id"})
public class ChildOtO {
    @Id
    @GeneratedValue
    Long id;

    String value;

    @OneToOne(/*mappedBy = "nested",*/ fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    ChildOtM parent;
}
