/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.Follow;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.models.projections.follow.FollowedOnly;
import com.koombea.twitterclone.web.app.models.projections.follow.FollowerOnly;
import com.koombea.twitterclone.web.app.models.projections.user.IdOnly;
import com.koombea.twitterclone.web.app.repositories.FollowRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    private final UserService userService;

    @Autowired
    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public Follow create(String followerUsername, String followedUsername) throws ValidationException {
        if (followerUsername.equals(followedUsername)) {
            throw new ValidationException("You can not follow yourself!");
        }

        User follower = userService.findUserByUsername(followerUsername);
        User followed = userService.findUserByUsername(followedUsername);
        Follow follow = new Follow(follower, followed, followerUsername);
        if (followRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId())) {
            throw new ValidationException("You are following this user");
        }

        return followRepository.save(follow);
    }

    public Long countFollowersByFollowedId(String followedId) {
        return followRepository.countByFollowedId(followedId);
    }

    public Long countFollowedByFollowerId(String followerId) {
        return followRepository.countByFollowerId(followerId);
    }

    public Page<FollowedOnly> getPaginatedFollowedByUsername(String username, Pageable pageable) {
        IdOnly user = userService.findIdByUsername(username);
        return followRepository.findAllByFollowerId(user.getId(), pageable, FollowedOnly.class);
    }

    public Page<FollowerOnly> getPaginatedFollowersByUsername(String username, Pageable pageable) {
        IdOnly user = userService.findIdByUsername(username);
        return followRepository.findAllByFollowedId(user.getId(), pageable, FollowerOnly.class);
    }
}
