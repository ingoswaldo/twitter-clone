/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */

package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.projections.user.NamesWithIdOnly;
import com.koombea.twitterclone.web.app.services.FollowService;
import com.koombea.twitterclone.web.app.services.PostService;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class IndexController {
    private final PostService postService;

    private final UserService userService;

    private final FollowService followService;

    @Autowired
    public IndexController(PostService postService, UserService userService, FollowService followService) {
        this.postService = postService;
        this.userService = userService;
        this.followService = followService;
    }

    @GetMapping({"", "/"})
    @Transactional(readOnly = true)
    public String index(Model model, Authentication authentication) {
        try {
            NamesWithIdOnly user = userService.findNamesByUsername(authentication.getName());
            model.addAttribute("totalPosts", postService.countByUserId(user.getId()));
            model.addAttribute("totalFollowed", followService.countFollowedByFollowerId(user.getId()));
            model.addAttribute("totalFollowers", followService.countFollowersByFollowedId(user.getId()));
            return "index";
        } catch (EntityNotFoundException exception) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
