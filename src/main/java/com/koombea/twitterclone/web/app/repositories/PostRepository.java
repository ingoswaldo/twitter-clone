/*
 * @creator: Oswaldo Montes
 * @date: November 15, 2023
 *
 */
package com.koombea.twitterclone.web.app.repositories;

import com.koombea.twitterclone.web.app.models.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Long countPostByUserId(String userId);

    <T> Page<T> findAllByUserId(String userId, Pageable pageable, Class<T> type);
}
