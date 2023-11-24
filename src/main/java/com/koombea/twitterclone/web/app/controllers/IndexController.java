/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */

package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.services.FollowService;
import com.koombea.twitterclone.web.app.services.PostService;
import com.koombea.twitterclone.web.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class IndexController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @GetMapping({"", "/"})
    @Transactional(readOnly = true)
    public String index(Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("totalPosts", postService.countByUserId(user.getId()));
        model.addAttribute("totalFollowed", followService.countFollowedByFollowerId(user.getId()));
        model.addAttribute("totalFollowers", followService.countFollowersByFollowedId(user.getId()));
        return "index";
    }
}
