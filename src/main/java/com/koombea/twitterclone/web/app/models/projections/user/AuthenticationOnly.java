/*
 * @creator: Oswaldo Montes
 * @date: November 28, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.projections.user;

import com.koombea.twitterclone.web.app.models.entities.Role;

import java.util.List;

public interface AuthenticationOnly {
    String getUsername();

    String getPassword();

    Boolean getEnabled();

    List<Role> getRoles();
}
