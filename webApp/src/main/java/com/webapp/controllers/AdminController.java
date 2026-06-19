package com.webapp.controllers;

import com.webapp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SessionService sessionService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("currentUser", sessionService.sessionUser());
        model.addAttribute("nbUsers", 0);
        model.addAttribute("nbPrestataires", 0);
        model.addAttribute("nbRdv", 0);
        model.addAttribute("nbAlertes", 0);
        return "admin-dashboard";
    }
}