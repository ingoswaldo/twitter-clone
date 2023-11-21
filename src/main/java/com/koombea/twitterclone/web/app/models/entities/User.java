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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@PasswordMatches
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = {"email", "username"})},
        indexes = { @Index(name = "email_index", columnList = "email"), @Index(name = "username_index", columnList = "username")})
public class User implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Getter
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Email
    @Getter
    @NotEmpty
    @Setter
    @Unique(fieldName = "email")
    @Column(nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    private String email;

    @Getter
    @NotEmpty
    @Setter
    @Column(nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    @Unique(fieldName = "username")
    @Size(min = 3)
    private String username;

    @Getter
    @NotEmpty
    @Setter
    @Size(min = 3)
    @Column(nullable = false)
    @ColumnTransformer(write = "LOWER(?)")
    private String fullName;

    @Getter
    @NotEmpty
    @Setter
    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Getter
    @Setter
    @Transient
    private String passwordConfirmation;

    @Getter
    @Setter
    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean enabled;

    @CreationTimestamp
    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

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
}
