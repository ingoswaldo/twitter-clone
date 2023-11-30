/*
 * @creator: Oswaldo Montes
 * @date: November 15, 2023
 *
 */
package com.koombea.twitterclone.web.app.repositories;

import com.koombea.twitterclone.web.app.models.entities.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, String> {
    Long countByFollowerId(String followerId);

    Long countByFollowedId(String followedId);

    Boolean existsByFollowerIdAndFollowedId(String followerId, String followedId);

    <T> Page<T> findAllByFollowerId(String followerId, Pageable pageable, Class<T> type);

    <T> Page<T> findAllByFollowedId(String followedId, Pageable pageable, Class<T> type);

    <T> Optional<T> findOneByFollowerIdAndFollowedId(String followerId, String followedId, Class<T> type);
}
