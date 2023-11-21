/*
 * @creator: Oswaldo Montes
 * @date: November 14, 2023
 *
 */

package com.koombea.twitterclone.web.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }
}
