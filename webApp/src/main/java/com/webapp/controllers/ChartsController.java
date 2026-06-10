package com.webapp.controllers;



import com.webapp.models.User;
import com.webapp.services.SessionService;
import com.webapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;


import java.util.List;



@Controller
public class ChartsController {

//
//    private final SessionService sessionService;
//    private final RdvRepository rdvRepository;
//
//    public ChartsController(UserService userService, SessionService sessionService, RdvRepository rdvRepository) {
//
//        this.sessionService = sessionService;
//        this.rdvRepository = rdvRepository;
//
//    }
//
//    @GetMapping("/charts")
//    public ModelAndView charts() {
//
//        User user = sessionService.sessionUser();
//
//        if (user == null) {
//            return new ModelAndView("redirect:/signin");
//        }
//
//        List<Object[]> results = rdvRepository.callSpRdvList(user.getIdUser(), 2026);
//
//        String[] moisLabels = {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin",
//                "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};
//
//        List<Integer> santeData = new ArrayList<>();
//        List<Integer> beauteData = new ArrayList<>();
//        List<Integer> soinData = new ArrayList<>();
//        List<Integer> administratifData = new ArrayList<>();
//
//        for (Object[] row : results) {
//            santeData.add((Integer) row[1]);
//            beauteData.add((Integer) row[2]);
//            soinData.add((Integer) row[3]);
//            administratifData.add((Integer) row[4]);
//        }
//
//        ModelAndView mav = new ModelAndView("charts");
//        mav.addObject("labels", moisLabels);
//        mav.addObject("sante", santeData);
//        mav.addObject("beaute", beauteData);
//        mav.addObject("soin", soinData);
//        mav.addObject("administratif", administratifData);
//
//        return mav;
//    }



}
