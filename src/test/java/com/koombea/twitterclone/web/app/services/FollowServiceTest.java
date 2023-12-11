package com.koombea.twitterclone.web.app.services;/*
 * @creator: Oswaldo Montes
 * @date: December 07, 2023
 *
 */

import com.koombea.twitterclone.web.app.AbstractIntegrationTest;
import com.koombea.twitterclone.web.app.models.entities.Follow;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.models.projections.follow.FollowedSummary;
import com.koombea.twitterclone.web.app.models.projections.follow.FollowerSummary;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FollowServiceTest extends AbstractIntegrationTest {
    private final FollowService followService;

    private final UserService userService;

    @Autowired
    public FollowServiceTest(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @BeforeAll
    static void setUp(@Autowired UserService userService) {
        userService.create("follower@example.com", "follower", "follower name", "follower");
        userService.create("followed@example.com", "followed", "followed name", "followed");
    }

    @Test
    @Order(1)
    void shouldCreateByUsernames() throws ValidationException {
        Follow followCreated = followService.createByUsernames("follower", "followed");
        assertNotNull(followCreated.getId());
    }

    @Test
    void shouldNotCreateDuplicatedFollowByUsernames() throws ValidationException {
        assertThrows(ValidationException.class, () -> followService.createByUsernames("follower", "followed"));
    }

    @Test
    void shouldNotCreateSelfFollowByUsernames() throws ValidationException {
        assertThrows(ValidationException.class, () -> followService.createByUsernames("follower", "follower"));
    }

    @Test
    @Order(2)
    void shouldCreateByUsernameAndFollowed() {
        Follow followCreated = followService.createByUsernameAndFollowed("user", getFollowed());
        assertNotNull(followCreated.getId());
    }

    @Test
    void shouldNotCreateDuplicatedByUsernameAndFollowed() {
        assertThrows(ValidationException.class, () -> followService.createByUsernameAndFollowed("user", getFollowed()));
    }

    @Test
    void shouldNotCreateSelfFollowByUsernameAndFollowed() {
        assertThrows(ValidationException.class, () -> followService.createByUsernameAndFollowed("followed", getFollowed()));
    }

    @Test
    @Order(4)
    void shouldCountFollowersByFollowedId() {
        assertEquals(followService.countFollowersByFollowedId(getUserIdByUsername("followed")), 2);
    }

    @Test
    @Order(5)
    void shouldCountFollowedByFollowerId() {
        assertEquals(followService.countFollowersByFollowedId(getUserIdByUsername("follower")), 1);
    }

    @Test
    void shouldGetPaginatedFollowedSummaryByUsername() {
        Page<FollowedSummary> result = followService.getPaginatedFollowedSummaryByUsername("follower", getPageable());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getContent().getFirst().getFollowedUsername(), "followed");
    }

    @Test
    void shouldGetPaginatedFollowersSummaryByUsername() {
        Page<FollowerSummary> result = followService.getPaginatedFollowersSummaryByUsername("followed", getPageable());
        assertEquals(result.getTotalElements(), 2);
        assertEquals(result.getContent().getFirst().getFollowerUsername(), "follower");
    }

    @Test
    @Order(3)
    void shouldFindFollowedBack() {
        followService.createByUsernames("followed", "follower");
        assertTrue(followService.findFollowedBack(getUserIdByUsername("followed"), getUserIdByUsername("follower")).isPresent());
    }

    @Test
    void shouldNotFindFollowedBack() {
        assertTrue(followService.findFollowedBack(getUserIdByUsername("followed"), getUserIdByUsername("user")).isEmpty());
    }

    @Test
    void shouldFollow() {
        assertTrue(followService.isFollowed(getUserIdByUsername("follower"), getUserIdByUsername("followed")));
    }

    @Test
    void shouldNotFollow() {
        assertFalse(followService.isFollowed(getUserIdByUsername("followed"), getUserIdByUsername("user")));
    }

    @Test
    void shouldMarkAsFollowBack() {
        Follow followCreated = followService.findFollowedBack(getUserIdByUsername("followed"), getUserIdByUsername("follower")).get();
        followService.markAsFollowBack(followCreated);
        assertTrue(followService.findFollowedBack(getUserIdByUsername("follower"), getUserIdByUsername("followed")).get().getIsFollowBack());
    }

    @Test
    void shouldNotMarkAsFollowBack() {
        Follow followCreated = followService.createByUsernames("user", "follower");
        followService.markAsFollowBack(followCreated);
        assertFalse(followService.findFollowedBack(getUserIdByUsername("user"), getUserIdByUsername("follower")).get().getIsFollowBack());
    }

    private User getFollowed() {
        return userService.findUserByUsername("followed");
    }

    private String getUserIdByUsername(String username) {
        return userService.findIdByUsername(username).getId();
    }
}