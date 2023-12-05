/*
 * @creator: Oswaldo Montes
 * @date: November 17, 2023
 *
 */
package com.koombea.twitterclone.web.app.services;

import com.koombea.twitterclone.web.app.models.entities.Post;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.models.projections.post.MessageOnly;
import com.koombea.twitterclone.web.app.models.projections.post.PostSummary;
import com.koombea.twitterclone.web.app.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post create(String username, String message) {
        User user = userService.findUserByUsername(username);
        return postRepository.save(new Post(user, message));
    }

    public Long countByUserId(String userId) {
        return postRepository.countPostByUserId(userId);
    }

    public Page<MessageOnly> getPaginatedPostsByUserId(String userId, Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable, MessageOnly.class);
    }

    public Page<PostSummary> getPaginatedPostsWithFollowedUserPostsByUserId(String userId, Pageable pageable) {
        return postRepository.findAllFeedsByUserId(userId, pageable, PostSummary.class);
    }
}
