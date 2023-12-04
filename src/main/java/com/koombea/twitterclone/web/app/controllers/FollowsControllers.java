/*
 * @creator: Oswaldo Montes
 * @date: November 21, 2023
 *
 */
package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.entities.Follow;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.services.FollowService;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionScope
@SessionAttributes("follow")
@RequestMapping("/follows")
@Secured("ROLE_USER")
public class FollowsControllers {

    private final FollowService followService;

    private final UserService userService;

    @Autowired
    public FollowsControllers(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @GetMapping("/followers")
    public String indexFollowers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("follower.fullName"));
        model.addAttribute("followers", followService.getPaginatedFollowersSummaryByUsername(authentication.getName(), pageable));
        return "follows/index-follower";
    }

    @GetMapping("/followed")
    public String indexFollowed(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("followed.fullName"));
        model.addAttribute("followedUsers", followService.getPaginatedFollowedSummaryByUsername(authentication.getName(), pageable));
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
            Follow followCreated = followService.createByUsernames(authentication.getName(), follow.getUsername());
            if (followCreated.getId() == null) message = "🚨 User was not followed!";

        } catch (ValidationException exception) {
            result.addError(new ObjectError("follow", exception.getMessage()));
            return "follows/new";
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/follows/followed";
    }

    @PostMapping("/add")
    public String create(@RequestParam String followedId, @RequestParam(required = false, defaultValue = "") String redirectUrlMapping, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            User followed = userService.findUserById(followedId);
            String message = "✅ User followed!";
            Follow followCreated = followService.createByUsernameAndFollowed(authentication.getName(), followed);
            if (followCreated.getId() == null) message = "🚨 User was not followed!";

            redirectAttributes.addFlashAttribute("message", message);
            if (!redirectUrlMapping.isEmpty()) return "redirect:/" + redirectUrlMapping;

            return "redirect:/" + followed.getUsername();
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (ValidationException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
            return "redirect:/follows/followed";
        }
    }
}
