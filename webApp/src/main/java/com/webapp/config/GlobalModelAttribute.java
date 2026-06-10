package com.webapp.config;

import com.webapp.models.User;
import com.webapp.services.SessionService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    private final SessionService sessionService;

    public GlobalModelAttribute(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ModelAttribute("currentUser")
    public User currentUser() {
        try {
            return sessionService.sessionUser();
        } catch (Exception e) {
            return null; // utilisateur non connecté
        }
    }
}