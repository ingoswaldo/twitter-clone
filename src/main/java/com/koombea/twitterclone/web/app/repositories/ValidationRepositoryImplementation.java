/*
 * @creator: Oswaldo Montes
 * @date: November 23, 2023
 *
 */
package com.koombea.twitterclone.web.app.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class ValidationRepositoryImplementation implements ValidationRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existBy(String tableName, String columnName, Object value) {
        Query query = entityManager.createQuery("SELECT 1 FROM " + tableName +" WHERE " + columnName +" = :value");
        query.setParameter("value", value);

        return !query.getResultList().isEmpty();
    }
}
