/*
 * @creator: Oswaldo Montes
 * @date: December 07, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.AbstractIntegrationTest;
import com.koombea.twitterclone.web.app.models.entities.Post;
import com.koombea.twitterclone.web.app.models.projections.post.MessageOnly;
import com.koombea.twitterclone.web.app.models.projections.post.PostSummary;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest extends AbstractIntegrationTest {
    private final UserService userService;

    private final PostService postService;

    @Autowired
    public PostServiceTest(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Test
    @Order(1)
    void shouldCreate() {
        Post post = postService.create("user", "message");
        assertNotNull(post.getId());
    }

    @Test
    void shouldNotCreateWithoutUsername() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> postService.create("", "message"));
    }

    @Test
    void shouldNotCreateWithoutMessage() {
        try {
            postService.create("user", "");
            fail("Expected exception to be thrown");
        } catch (Exception exception) {
            assertInstanceOf(TransactionSystemException.class, exception);
        }
    }

    @Test
    void shouldCountByUserId() {
        assertEquals(postService.countByUserId(getUserId()), 1);
    }

    @Test
    void shouldGetPaginatedPostsByUserId() {
        Page<MessageOnly> result = postService.getPaginatedPostsByUserId(getUserId(), getPageable());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getContent().getFirst().getMessage(), "message");
    }

    @Test
    void shouldGetPaginatedPostsWithFollowedUserPostsByUserId() {
        Page<PostSummary> result = postService.getPaginatedPostsWithFollowedUserPostsByUserId(getUserId(), getPageable());
        assertEquals(result.getTotalElements(), 1);
        assertEquals(result.getContent().getFirst().getMessage(), "message");
    }

    protected String getUserId() {
        return userService.findIdByUsername("user").getId();
    }
}