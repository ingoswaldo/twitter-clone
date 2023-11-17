package com.koombea.twitterclone.web.app.repositories;

import com.koombea.twitterclone.web.app.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*
 * @creator: Oswaldo Montes
 * @date: November 15, 2023
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
