package com.webapp.controllers;

import com.webapp.services.AdminService;
import com.webapp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
public class AdminStatsController {

    private final AdminService adminService;
    private final SessionService sessionService;

    @GetMapping
    public String stats(Model model) {
        model.addAttribute("currentUser", sessionService.sessionUser());
        try {
            model.addAttribute("mongoStats", adminService.getMongoStats());
        } catch (Exception e) {
            model.addAttribute("mongoStats", null);
        }
        return "admin-stats";
    }
}