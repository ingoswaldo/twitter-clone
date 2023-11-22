/*
 * @creator: Oswaldo Montes
 * @date: November 21, 2023
 *
 */
package com.koombea.twitterclone.web.app.controllers;

import com.koombea.twitterclone.web.app.models.entities.Post;
import com.koombea.twitterclone.web.app.models.entities.User;
import com.koombea.twitterclone.web.app.services.PostService;
import com.koombea.twitterclone.web.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionScope
@SessionAttributes("post")
@RequestMapping("/tweets")
@Secured("ROLE_USER")
public class PostsControllers {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "tweets/new";
    }

    @PostMapping("")
    public String create(@Valid Post post, BindingResult result, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        model.addAttribute("post", post);
        if (result.hasErrors()) return "tweets/new";

        User user = userService.findByUsername(authentication.getName());
        String message = "✅ Tweet posted!";
        if (postService.create(user, post.getMessage()).getId().isEmpty()) message = "🚨 Tweet does not posted!";

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }
}
