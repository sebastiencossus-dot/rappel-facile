package com.webapp.controllers;

import com.webapp.services.AdminPrestataireService;
import com.webapp.services.AdminRdvService;
import com.webapp.services.AdminUserService;
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
    private final AdminUserService adminUserService;
    private final AdminPrestataireService adminPrestataireService;
    private final AdminRdvService adminRdvService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("currentUser", sessionService.sessionUser());
        model.addAttribute("nbUsers", adminUserService.findAll().size());
        model.addAttribute("nbPrestataires", adminPrestataireService.findAll().size());
        model.addAttribute("nbRdv", adminRdvService.findAll().size());

        // temporaire si tu n’as pas encore de service
        model.addAttribute("nbAlertes", 0);
        return "admin-dashboard";
    }
}