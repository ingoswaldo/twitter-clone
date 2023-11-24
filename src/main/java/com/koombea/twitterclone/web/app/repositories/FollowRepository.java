/*
 * @creator: Oswaldo Montes
 * @date: November 15, 2023
 *
 */
package com.koombea.twitterclone.web.app.repositories;

import com.koombea.twitterclone.web.app.models.entities.Follow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends CrudRepository<Follow, String> {
    Long countByFollowerId(String followerId);

    Long countByFollowedId(String followedId);

    Boolean existsByFollowerIdAndFollowedId(String followerId, String followedId);
}
