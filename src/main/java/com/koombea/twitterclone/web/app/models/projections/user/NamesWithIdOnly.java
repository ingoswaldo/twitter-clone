/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.user;

public interface NamesWithIdOnly {
    String getId();

    String getFullName();

    String getUsername();

    default String humanizeFullName() {
        return getFullName().substring(0,1).toUpperCase().concat(getFullName().substring(1));
    }
}
