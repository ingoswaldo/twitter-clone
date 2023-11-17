package com.koombea.twitterclone.web.app.models.entities;

import com.koombea.twitterclone.web.app.repositories.UserRepository;
import com.koombea.twitterclone.web.app.validations.PasswordMatches;
import com.koombea.twitterclone.web.app.validations.Unique;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */
@Entity
@PasswordMatches
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = {"email", "username"})},
        indexes = { @Index(name = "email_index", columnList = "email"), @Index(name = "username_index", columnList = "username")})
public class User implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotEmpty
    @Email
    @Unique(fieldName = "email")
    @Column(nullable = false)
    @ColumnTransformer(forColumn = "email", write = "LOWER(email)")
    private String email;

    @NotEmpty
    @Column(nullable = false)
    @ColumnTransformer(forColumn = "username", write = "LOWER(username)")
    @Unique(fieldName = "username")
    @Size(min = 3)
    private String username;

    @NotEmpty
    @Size(min = 3)
    @Column(nullable = false)
    @ColumnTransformer(forColumn = "full_name",write = "LOWER(full_name)")
    private String fullName;

    @NotEmpty
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @Transient
    private String passwordConfirmation;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public User() {
        this.email = "";
        this.username = "";
        this.fullName = "";
        this.password = "";
        this.passwordConfirmation = "";
    }

    public User(String email, String username, String fullName, String password, String passwordConfirmation) {
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
