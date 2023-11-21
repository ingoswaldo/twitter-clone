/*
 * @creator: Oswaldo Montes
 * @date: November 21, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts", indexes = { @Index(name = "user_id_index", columnList = "user_id") })
public class Post extends BaseEntity {
    @Getter
    @NotEmpty
    @Size(max = 280)
    @Setter
    @Column(nullable = false, length = 280)
    private String message;

    @Getter
    @NotNull
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    public Post() {
        this.user = new User();
        this.message = "";
    }

    public Post(User user, String message) {
        this.message = message;
        this.user = user;
    }
}
