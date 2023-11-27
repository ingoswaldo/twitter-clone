/*
 * @creator: Oswaldo Montes
 * @date: November 27, 2023
 *
 */
package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.services.FollowService;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/{username}")
public class ProfileController {
    private final UserService userService;

    private final FollowService followService;

    @Autowired
    public ProfileController(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @GetMapping("")
    public String profile(@PathVariable String username, Model model) {
        try {
            User userProfile = userService.findByUsername(username);
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("totalFollowers", followService.countFollowersByFollowedId(userProfile.getId()));
            model.addAttribute("totalFollowed", followService.countFollowedByFollowerId(userProfile.getId()));
            return "users/profile";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}
