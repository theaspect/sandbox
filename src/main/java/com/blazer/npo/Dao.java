package com.blazer.npo;

import com.blazer.npo.domain.*;
import com.google.inject.Inject;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@MappedSuperclass
@NamedQueries({
        @NamedQuery(name = "Parent.findAll", query = "from Parent"),
        @NamedQuery(name = "ChildOtM.findAll", query = "from ChildOtM"),
        @NamedQuery(name = "ChildMtM.findAll", query = "from ChildMtM"),
        @NamedQuery(name = "ChildOtO.findAll", query = "from ChildOtO"),
        @NamedQuery(name = "ChildOtMNested.findAll", query = "from ChildOtMNested"),

        @NamedQuery(name = "Parent.joinMtM", query = "Select p from Parent p LEFT JOIN FETCH p.childMtM c"),
        @NamedQuery(name = "Parent.joinOtM", query = "Select p from Parent p LEFT JOIN p.childOtM c"),
        @NamedQuery(name = "Parent.joinOtMFetch", query = "Select p from Parent p LEFT JOIN FETCH p.childOtM c"),
        @NamedQuery(name = "Parent.joinOtMtO", query = "Select p from Parent p LEFT JOIN FETCH p.childOtM c JOIN FETCH c.nested n"),

        @NamedQuery(name = "ChildOtM.joinOtO", query = "Select c from ChildOtM c JOIN FETCH c.nested n"),
        @NamedQuery(name = "ChildMtM.joinOtMNested", query = "Select c from ChildMtM c LEFT JOIN FETCH c.nested n"),
})
public class Dao {
    @Getter EntityManager entityManager;

    @Inject
    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Parent> parentsFindAll() {
        return entityManager.createNamedQuery("Parent.findAll", Parent.class).getResultList();
    }

    public List<ChildOtM> childOtMFindAll() {
        return entityManager.createNamedQuery("ChildOtM.findAll", ChildOtM.class).getResultList();
    }

    public List<ChildMtM> childMtMFindAll() {
        return entityManager.createNamedQuery("ChildMtM.findAll", ChildMtM.class).getResultList();
    }

    public List<ChildOtO> childOtOFindAll() {
        return entityManager.createNamedQuery("ChildOtO.findAll", ChildOtO.class).getResultList();
    }

    public List<ChildOtMNested> childOtMNestedFindAll() {
        return entityManager.createNamedQuery("ChildOtMNested.findAll", ChildOtMNested.class).getResultList();
    }

    public List<Parent> parentsJoinMtM() {
        return entityManager.createNamedQuery("Parent.joinMtM", Parent.class).getResultList();
    }

    public List<Parent> parentsJoinOtM() {
        return entityManager.createNamedQuery("Parent.joinOtM", Parent.class).getResultList();
    }

    public List<Parent> parentsJoinOtMFetch() {
        return entityManager.createNamedQuery("Parent.joinOtMFetch", Parent.class).getResultList();
    }

    public List<ChildOtM> childOtMJoinOtO() {
        return entityManager.createNamedQuery("ChildOtM.joinOtO", ChildOtM.class).getResultList();
    }

    public List<ChildMtM> childMtMJoinOtMNested() {
        return entityManager.createNamedQuery("ChildMtM.joinOtMNested", ChildMtM.class).getResultList();
    }

    public List<Parent> parentsOtMJoinOtMtO() {
        return entityManager.createNamedQuery("Parent.joinOtMtO", Parent.class).getResultList();
    }
}
