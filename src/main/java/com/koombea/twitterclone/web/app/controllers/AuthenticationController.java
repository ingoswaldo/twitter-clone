package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;

/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */
@Controller
@SessionScope
@SessionAttributes("user")
public class AuthenticationController {
    @Autowired
    private UserService service;

    @GetMapping({"/sign-up"})
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String create(@Valid User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) return "auth/sign-up";

        service.create(user.getEmail(), user.getUsername(), user.getFullName(), user.getPassword());
        return "redirect:/login";
    }
}
