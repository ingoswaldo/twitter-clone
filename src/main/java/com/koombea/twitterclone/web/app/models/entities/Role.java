/*
 * @creator: Oswaldo Montes
 * @date: November 20, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "authorities", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "authority" }) })
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

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
