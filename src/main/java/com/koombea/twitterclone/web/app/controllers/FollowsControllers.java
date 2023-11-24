/*
 * @creator: Oswaldo Montes
 * @date: November 21, 2023
 *
 */
package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.entities.Follow;
import com.koombea.twitterclone.web.app.services.FollowService;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionScope
@SessionAttributes("follow")
@RequestMapping("/follows")
@Secured("ROLE_USER")
public class FollowsControllers {
    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @GetMapping("/followers")
    public String indexFollowers(Model model, Authentication authentication) {
        model.addAttribute("followers", followService.getFollowersSortedByUsernameLoggedIn(authentication.getName()));
        return "follows/index-follower";
    }

    @GetMapping("/followed")
    public String indexFollowed(Model model, Authentication authentication) {
        model.addAttribute("followedUsers", followService.getFollowedSortedByUsernameLoggedIn(authentication.getName()));
        return "follows/index-followed";
    }

    @GetMapping("/new")
    public String followForm(Model model) {
        model.addAttribute("follow", new Follow());
        return "follows/new";
    }

    @PostMapping("/new")
    public String create(@Valid Follow follow, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        model.addAttribute("follow", follow);
        if (result.hasErrors()) return "follows/new";

        String message = "✅ User followed!";

        try {
            Follow followCreated = followService.create(authentication.getName(), follow.getUsername());
            if (followCreated.getId() == null) message = "🚨 User was not followed!";

        } catch (ValidationException exception) {
            result.addError(new ObjectError("follow", exception.getMessage()));
            return "follows/new";
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/follows/followed";
    }
}
