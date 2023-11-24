/*
 * @creator: Oswaldo Montes
 * @date: November 23, 2023
 *
 */
package com.koombea.twitterclone.web.app.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRepository {
    boolean existBy(String tableName, String columnName, Object value);
}
