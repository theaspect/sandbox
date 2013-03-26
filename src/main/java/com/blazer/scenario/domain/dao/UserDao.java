package com.blazer.scenario.domain.dao;

import com.blazer.scenario.domain.User;

import javax.persistence.EntityManager;

/**
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class UserDao {
    EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public User findUserByName(String name) {
        // TODO
        return null;
    }

    public User findUserByTerminal(String terminal) {
        // TODO
        return null;
    }
}
