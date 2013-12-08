package com.blazer.repl.service;

import com.blazer.repl.domain.Contact;
import com.blazer.repl.domain.GsonExclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

import javax.inject.Provider;
import javax.persistence.*;
import java.util.List;

/** @author Constantine Linnick <theaspect@gmail.com> */
@MappedSuperclass
@NamedQueries({
        @NamedQuery(name = "Contact.findAll", query = "SELECT DISTINCT c from Contact c LEFT JOIN FETCH c.info i")
})
public class Dao {
    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public Dao(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public List<Contact> findAll() {
        EntityManager entityManager = entityManagerProvider.get();
        TypedQuery<Contact> query = entityManager.createNamedQuery("Contact.findAll", Contact.class);
        List<Contact> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public void saveAll(List<Contact> contacts) {
        EntityManager entityManager = entityManagerProvider.get();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            for (Contact c : contacts) {
                entityManager.persist(c);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public static String toGson(List<Contact> contacts) {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }

                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getAnnotation(GsonExclude.class) != null;
                    }
                })
                .serializeNulls()
                .create().toJson(contacts);
    }
}
