package com.blazer.npo;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class NPOModel extends AbstractModule {
    @Override
    protected void configure() {
        bind(NPOApp.class);
        bind(Dao.class);
    }

    @Provides
    @Singleton
    EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("NPO");
    }

    @Provides
    @Inject
    EntityManager entityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }
}
