/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */
package com.koombea.twitterclone.web.app.models.entities;

import com.koombea.twitterclone.web.app.validations.PasswordMatches;
import com.koombea.twitterclone.web.app.validations.Unique;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;

@Getter
@Setter
@Entity
@PasswordMatches
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = {"email", "username"})},
        indexes = { @Index(name = "email_index", columnList = "email"), @Index(name = "username_index", columnList = "username")})
public class User extends BaseEntity {
    @Email
    @NotEmpty
    @Unique(entityName = "User", columnName = "email")
    @Column(nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    private String email;

    @NotEmpty
    @Column(nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    @Unique(entityName = "User", columnName = "username")
    @Size(min = 3)
    private String username;

    @NotEmpty
    @Size(min = 3)
    @Column(nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    private String fullName;

    @NotEmpty
    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean enabled;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follow> followers;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL)
    private List<Follow> followed;

    public User() {
        this.email = "";
        this.username = "";
        this.fullName = "";
        this.password = "";
        this.passwordConfirmation = "";
        this.enabled = true;
    }

    public User(String email, String username, String fullName, String password, String passwordConfirmation) {
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.enabled = true;
    }

    public String humanizeFullName() {
        String name = this.fullName;
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
