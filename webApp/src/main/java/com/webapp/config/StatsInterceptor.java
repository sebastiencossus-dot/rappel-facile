// StatsInterceptor.java dans webapp
package com.webapp.config;


import com.webapp.services.MsstatsClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class StatsInterceptor implements HandlerInterceptor {

    @Autowired
    private MsstatsClient msstatsClient;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()
                    && !"anonymousUser".equals(auth.getName())) {

                String page = request.getRequestURI();
                // Ne pas tracker les ressources statiques
                if (!page.startsWith("/css") && !page.startsWith("/js")
                        && !page.startsWith("/images") && !page.startsWith("/actuator")) {

                    msstatsClient.recordVisite(Map.of(
                            "userEmail", auth.getName(),
                            "page", page
                    ));
                }
            }
        } catch (Exception e) {
            // Ne pas bloquer si msstats indisponible
        }
        return true;
    }
}