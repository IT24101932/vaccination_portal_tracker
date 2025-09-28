package com.vaccination_portal_tracker.vaccination_portal_tracker.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/home.html"; // Redirect to static file
    }

    @GetMapping("/wallet")
    public String wallet() {
        return "redirect:/wallet.html"; // Redirect to static file
    }
}
