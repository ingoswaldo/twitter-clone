/*
 * @creator: Oswaldo Montes
 * @date: November 20, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorities", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "authority" }) })
public class Role extends BaseEntity {
    @Getter
    @Setter
    private String authority;

    public Role() {
        this.authority = "";
    }

    public Role(String authority) {
        this.authority = authority;
    }
}
