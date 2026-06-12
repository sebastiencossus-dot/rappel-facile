package com.webapp.controllers;



import com.webapp.models.User;
import com.webapp.services.MsJpaClient;
import com.webapp.services.SessionService;
import com.webapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

@Controller
public class ChartsController {

    private SessionService sessionService;
    private MsJpaClient msjpaClient;

    public ChartsController(SessionService sessionService, MsJpaClient msjpaClient) {
        this.sessionService = sessionService;
        this.msjpaClient = msjpaClient;
    }

    @GetMapping("/charts")
    public ModelAndView charts() {

        User user = sessionService.sessionUser();

        if (user == null) {
            return new ModelAndView("redirect:/signin");
        }

        Map<String, List<Integer>> stats = msjpaClient.getRdvStats((long) user.getIdUser(), 2026);

        String[] moisLabels = {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};

        ModelAndView mav = new ModelAndView("charts");
        mav.addObject("labels",         moisLabels);
        mav.addObject("medical",          stats.get("medical"));
        mav.addObject("beaute",         stats.get("beaute"));
        mav.addObject("soin",           stats.get("soin"));
        mav.addObject("administratif",  stats.get("administratif"));

        return mav;
    }
}