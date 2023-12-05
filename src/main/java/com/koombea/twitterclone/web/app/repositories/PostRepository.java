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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Long countPostByUserId(String userId);

    <T> Page<T> findAllByUserId(String userIds, Pageable pageable, Class<T> type);

    @Query(value = "SELECT p.createdAt as createdAt, p.message as message, u.fullName as fullName, u.username as username FROM Post p INNER JOIN p.user u WHERE p.user.id = :userId OR p.user.id IN (SELECT f.followed.id FROM Follow f WHERE f.follower.id = :userId)")
    <T> Page<T> findAllFeedsByUserId(@Param("userId") String userId, Pageable pageable, Class<T> type);
}
