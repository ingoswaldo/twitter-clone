/*
 * @creator: Oswaldo Montes
 * @date: November 27, 2023
 *
 */
package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.projections.user.IdOnly;
import com.koombea.twitterclone.web.app.models.projections.user.NamesWithIdOnly;
import com.koombea.twitterclone.web.app.services.FollowService;
import com.koombea.twitterclone.web.app.services.PostService;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/{username}")
public class ProfileController {
    private final UserService userService;

    private final FollowService followService;

    private final PostService postService;

    @Autowired
    public ProfileController(UserService userService, FollowService followService, PostService postService) {
        this.userService = userService;
        this.followService = followService;
        this.postService = postService;
    }

    @GetMapping("")
    public String profile(@PathVariable String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, Authentication authentication) {
        if (authentication.getName().equals(username)) return "forward:/";

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            NamesWithIdOnly userProfile = userService.findNamesByUsername(username);
            IdOnly userAuthenticated = userService.findIdByUsername(authentication.getName());
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("totalFollowers", followService.countFollowersByFollowedId(userProfile.getId()));
            model.addAttribute("totalFollowed", followService.countFollowedByFollowerId(userProfile.getId()));
            model.addAttribute("isFollowed", followService.isFollowed(userAuthenticated.getId(), userProfile.getId()));
            model.addAttribute("tweets", postService.getPaginatedPostsByUserId(userProfile.getId(), pageable));
            return "users/profile";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/followers")
    public String followers(@PathVariable String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, Authentication authentication) {
        if (authentication.getName().equals(username)) return "forward:/follows/followers";

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("follower.fullName"));
            NamesWithIdOnly userProfile = userService.findNamesByUsername(username);
            IdOnly userAuthenticated = userService.findIdByUsername(authentication.getName());
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("userAuthenticated", userAuthenticated);
            model.addAttribute("followers", followService.getPaginatedFollowersSummaryByUsername(username, pageable));
            return "users/followers";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/followed")
    public String followed(@PathVariable String username, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, Authentication authentication) {
        if (authentication.getName().equals(username)) return "forward:/follows/followed";

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("followed.fullName"));
            NamesWithIdOnly userProfile = userService.findNamesByUsername(username);
            IdOnly userAuthenticated = userService.findIdByUsername(authentication.getName());
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("userAuthenticated", userAuthenticated);
            model.addAttribute("usersFollowed", followService.getPaginatedFollowedSummaryByUsername(username, pageable));
            return "users/followed";
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}
