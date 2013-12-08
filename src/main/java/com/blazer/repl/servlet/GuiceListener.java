package com.blazer.repl.servlet;

import com.blazer.repl.service.Dao;
import com.blazer.repl.service.GroovyService;
import com.google.inject.*;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class GuiceListener extends GuiceServletContextListener {
    @Getter private final Injector injector;

    public GuiceListener() {
        injector = Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/rest/*").with(RestServlet.class);
                serve("/repl/*").with(ReplServlet.class);

                bind(Dao.class);
                bind(GroovyService.class);
            }

            @Singleton
            @Provides
            EntityManagerFactory provideEntityManagerFactory() {
                return Persistence.createEntityManagerFactory("NPO");
            }

            @Provides
            @Inject
            EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
                return entityManagerFactory.createEntityManager();
            }
        });
    }
}
