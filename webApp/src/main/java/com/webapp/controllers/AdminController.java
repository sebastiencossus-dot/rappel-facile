package com.webapp.controllers;

import com.webapp.services.*;
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
    private final AdminUserService adminUserService;
    private final AdminPrestataireService adminPrestataireService;
    private final AdminRdvService adminRdvService;
    private final AdminService adminService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("currentUser", sessionService.sessionUser());
        model.addAttribute("nbUsers", adminUserService.findAll().size());
        model.addAttribute("nbPrestataires", adminPrestataireService.findAll().size());
        model.addAttribute("nbRdv", adminRdvService.findAll().size());
        model.addAttribute("mongoStats", adminService.getMongoStats());

        // temporaire si tu n’as pas encore de service
        model.addAttribute("nbAlertes", 0);
        return "admin-dashboard";
    }
}