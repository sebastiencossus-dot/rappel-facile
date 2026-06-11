package com.webapp.services;


import com.webapp.models.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final MsJpaClient msJpaClient;

    // ✅ Sans HttpSession
    public User sessionUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getName())) {
            throw new RuntimeException("NO_SESSION");
        }

        return msJpaClient.findUserByEmail(auth.getName());
    }
}