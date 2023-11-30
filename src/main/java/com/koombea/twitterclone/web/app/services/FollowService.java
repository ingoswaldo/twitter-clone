/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.events.follows.FollowCreatedEvent;
import com.koombea.twitterclone.web.app.models.entities.Follow;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.models.projections.follow.FollowedSummary;
import com.koombea.twitterclone.web.app.models.projections.follow.FollowerSummary;
import com.koombea.twitterclone.web.app.models.projections.user.IdOnly;
import com.koombea.twitterclone.web.app.repositories.FollowRepository;
import com.koombea.twitterclone.web.app.validations.groups.follow.CreateFollow;
import jakarta.validation.ValidationException;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    private final UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public Follow createByUsernames(String followerUsername, String followedUsername) throws ValidationException {
        validateSelfFollow(followerUsername, followedUsername);

        User follower = userService.findUserByUsername(followerUsername);
        User followed = userService.findUserByUsername(followedUsername);
        Follow follow = new Follow(follower, followed, followerUsername);
        validateDuplicatedFollow(follower, followed);

        return createFollowInDatabase(follow);
    }

    public Follow createByUsernameAndFollowed(String followerUsername, User followed) throws ValidationException {
        User follower = userService.findUserByUsername(followerUsername);
        Follow follow = new Follow(follower, followed, followerUsername);
        validateDuplicatedFollow(follower, followed);

        return createFollowInDatabase(follow);
    }

    public Long countFollowersByFollowedId(String followedId) {
        return followRepository.countByFollowedId(followedId);
    }

    public Long countFollowedByFollowerId(String followerId) {
        return followRepository.countByFollowerId(followerId);
    }

    public Page<FollowedSummary> getPaginatedFollowedOnlyByUsername(String username, Pageable pageable) {
        IdOnly user = userService.findIdByUsername(username);
        return followRepository.findAllByFollowerId(user.getId(), pageable, FollowedSummary.class);
    }

    public Page<FollowerSummary> getPaginatedFollowersOnlyByUsername(String username, Pageable pageable) {
        IdOnly user = userService.findIdByUsername(username);
        return followRepository.findAllByFollowedId(user.getId(), pageable, FollowerSummary.class);
    }

    public Optional<Follow> findFollowedBack(String followerId, String followedId) {
        return followRepository.findOneByFollowerIdAndFollowedId(followerId, followedId, Follow.class);
    }

    @Validated({CreateFollow.class, Default.class})
    public void markAsFollowBack(Follow followCreated) {
        Optional<Follow> followedBack = findFollowedBack(followCreated.getFollowed().getId(), followCreated.getFollower().getId());
        if (followedBack.isPresent()) {
            Follow follow = followedBack.get();
            follow.setIsFollowBack(true);
            followCreated.setIsFollowBack(true);
            followRepository.saveAll(List.of(followCreated, follow));
        }
    }

    private void validateSelfFollow(String followerUsername, String followedUsername) {
        if (followerUsername.equals(followedUsername)) {
            throw new ValidationException("You can not follow yourself!");
        }
    }

    private void validateDuplicatedFollow(User follower, User followed) {
        if (followRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId())) {
            throw new ValidationException("You are following this user");
        }
    }

    private Follow createFollowInDatabase(Follow follow) {
        Follow followCreated = followRepository.save(follow);
        eventPublisher.publishEvent(new FollowCreatedEvent(this, follow));

        return followCreated;
    }
}
