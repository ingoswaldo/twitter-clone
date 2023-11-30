/*
 * @creator: Oswaldo Montes
 * @date: November 22, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.entities;

import com.koombea.twitterclone.web.app.validations.Exists;
import com.koombea.twitterclone.web.app.validations.groups.follow.CreateFollow;
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
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean isFollowBack;

    @Exists(entityName = "User", columnName = "username", groups = CreateFollow.class)
    @NotEmpty(groups = CreateFollow.class)
    @Size(min = 3, groups = CreateFollow.class)
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
        this.isFollowBack = false;
    }

    public Follow(User follower, User followed, String username) {
        this.follower = follower;
        this.followed = followed;
        this.username = username;
        this.isFollowBack = false;
    }
}
