/*
 * @creator: Oswaldo Montes
 * @date: November 22, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.entities;

import com.koombea.twitterclone.web.app.validations.Exists;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "follows", uniqueConstraints = { @UniqueConstraint(columnNames = {"follower_id", "followed_id"})},
        indexes = { @Index(name = "follower_id_index", columnList = "follower_id"), @Index(name = "followed_id_index", columnList = "followed_id")})
public class Follow extends BaseEntity {
    @Exists(entityName = "User", columnName = "username")
    @NotEmpty
    @Size(min = 3)
    @Transient
    private String username;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User follower;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User followed;

    public Follow() {
        this.follower = new User();
        this.followed = new User();
        this.username = "";
    }

    public Follow(User follower, User followed, String username) {
        this.follower = follower;
        this.followed = followed;
        this.username = username;
    }
}
