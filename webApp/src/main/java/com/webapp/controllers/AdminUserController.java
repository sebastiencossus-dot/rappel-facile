package com.webapp.controllers;

import com.webapp.models.User;
import com.webapp.services.AdminUserService;
import com.webapp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;
    private final SessionService sessionService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = adminUserService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", sessionService.sessionUser());
        return "admin-users";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        User user = adminUserService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("currentUser", sessionService.sessionUser());
        return "admin-user-edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, User user) {
        adminUserService.update(id, user);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        adminUserService.delete(id);
        return "redirect:/admin/users";
    }
}