package com.blazer.scenario.domain.dao;

import com.blazer.scenario.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Stab class which loads Users from database
 * @author Constantine Linnick <theaspect@gmail.com>
 */
public class UserDao {
    EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public User findUserByName(String name) {
        // Query data from DB
        // ...
        //
        return null;
    }

    public User findUserByTerminal(String terminal) {
        // Query data from DB
        // ...
        //
        return null;
    }

    public List<User> findAll() {
        // Query data from DB
        // ...
        //
        return null;
    }
}
