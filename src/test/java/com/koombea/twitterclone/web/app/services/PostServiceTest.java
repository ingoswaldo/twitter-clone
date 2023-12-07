/*
 * @creator: Oswaldo Montes
 * @date: December 07, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.Post;
import com.koombea.twitterclone.web.app.models.projections.post.MessageOnly;
import com.koombea.twitterclone.web.app.models.projections.post.PostSummary;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {
    @Autowired
    UserService userService;

    @Autowired
    private PostService postService;

    @BeforeAll
    static void setUp(@Autowired UserService userService) {
        userService.create("user@example.com", "user", "full name", "password");
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

    private String getUserId() {
        return userService.findIdByUsername("user").getId();
    }

    private Pageable getPageable() {
        return PageRequest.of(0,2);
    }
}