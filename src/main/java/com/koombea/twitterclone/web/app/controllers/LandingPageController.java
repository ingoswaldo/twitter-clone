package com.koombea.twitterclone.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */
@Controller
public class LandingPageController {
    @GetMapping({"", "/"})
    public String index() {
        return "landing-page";
    }
}
