package com.webapp.controllers;



import com.webapp.models.RDV;
import com.webapp.models.User;
import com.webapp.services.MsJpaClient;
import com.webapp.services.RdvService;
import com.webapp.services.SessionService;
import com.webapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;


import java.util.List;
import java.util.Map;

@Controller
public class ChartsController {

    private SessionService sessionService;
    private MsJpaClient msjpaClient;
    private RdvService rdvService;

    public ChartsController(SessionService sessionService, MsJpaClient msjpaClient) {
        this.sessionService = sessionService;
        this.msjpaClient = msjpaClient;
        this.rdvService = rdvService;
    }

    @GetMapping("/charts")
    public ModelAndView charts() {

        User user = sessionService.sessionUser();

        if (user == null) {
            return new ModelAndView("redirect:/signin");
        }

        Map<String, List<Integer>> stats = msjpaClient.getRdvStats((long) user.getIdUser(), 2026);

        String[] moisLabels = {"Jan", "Fev", "Mar", "Avr", "Mai", "Juin",
                "Juil", "Aout", "Sept", "Oct", "Nov", "Dec"};

        ModelAndView mav = new ModelAndView("charts");
        mav.addObject("labels",         moisLabels);
        mav.addObject("medical",          stats.get("medical"));
        mav.addObject("beaute",         stats.get("beaute"));
        mav.addObject("soin",           stats.get("soin"));
        mav.addObject("administratif",  stats.get("administratif"));
        mav.addObject("effectue", stats.get("effectue"));
        mav.addObject("rate",     stats.get("rate"));
        mav.addObject("avenir",   stats.get("avenir"));

        return mav;
    }

//    @GetMapping("/details")
//    public List<RDV> getDetails(
//            @RequestParam int mois,
//            @RequestParam String type, // categorie OU statut
//            @RequestParam String valeur
//    ) {
//        return rdvService.getDetails(mois, type, valeur);
//    }

}