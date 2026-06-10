package com.webapp.controllers;

import com.webapp.models.RDV;
import com.webapp.models.User;
import com.webapp.services.*;
import com.webapp.services.form.rdvForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class RdvController {

    @Autowired
    RdvService rdvService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    MsJpaClient msJpaClient;

    @Autowired
    ProfessionService professionService;

    @Autowired
    PrestataireService prestataireService;

    @Autowired
    AdresseService adresseService;



    @GetMapping("/rdv")
    public ModelAndView index() {

        ModelAndView mav = new ModelAndView();

        User user = sessionService.sessionUser();
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }
        mav.addObject("currentUser", user);
        String email = user.getEmail();




        LocalDate today = LocalDate.now();

        List<RDV> rdvs = rdvService.getRdvByUser(user.getEmail());

        List<RDV> todayList = rdvs.stream()
                .filter(r -> r.getDateRdv().toLocalDate().equals(today))
                .sorted(Comparator.comparing(RDV::getDateRdv)) // tri par heure croissante
                .toList();

        List<RDV> attente = rdvs.stream()
                .filter(r -> r.getIsOK() == 1 && r.getDateRdv().toLocalDate().isBefore(today))
                .sorted(Comparator.comparing(RDV::getDateRdv)) // du plus ancien au plus récent
                .toList();

        List<RDV> autres = rdvs.stream()
                .filter(r -> r.getDateRdv().toLocalDate().isAfter(today))
                .sorted(Comparator.comparing(RDV::getDateRdv)) // du plus proche au plus loin
                .toList();

        mav.addObject("rdvJour", todayList);
        mav.addObject("rdvAttente", attente);
        mav.addObject("rdvAutres", autres);
        mav.setViewName("index");

        return mav;
    }

    @PostMapping("/rdv/add")
    public String createRdv(@ModelAttribute rdvForm form) {

        rdvService.createRdv(form);

        return "redirect:/rdv"; // ou ta page liste
    }

    @GetMapping("/rdv/add")
    public String createForm(Model model) {

        model.addAttribute("rdvForm", new rdvForm());

        model.addAttribute("prestataires", prestataireService.findAll());
        model.addAttribute("professions", professionService.findAll());
        model.addAttribute("adresses", adresseService.findAll());

        return "edit-rdv";
    }

    @PostMapping("/rdv/update/{id}")
    public String updateRdv(@PathVariable Integer id, RDV rdv) {
        rdvService.updateRdv(id, rdv);
        return "redirect:/rdv";
    }

    @GetMapping("/rdv/delete/{id}")
    public String deleteRdv(@PathVariable Integer id) {
        rdvService.deleteRdv(id);
        return "redirect:/rdv";
    }

    @GetMapping("/rdv/edit/{id}")
    public ModelAndView editRdv(@PathVariable Integer id) {

        String email = sessionService.sessionUser().getEmail();

        RDV rdv = msJpaClient.getRdv(id, email);

        if (rdv == null) {
            return new ModelAndView("redirect:/rdv");
        }

        return new ModelAndView("rdv-form", "rdv", rdv);
    }

    @GetMapping("/rdv/{id}")
    public ModelAndView detailRdv(@PathVariable Integer id) {

        String email = sessionService.sessionUser().getEmail();
        RDV rdv = msJpaClient.getRdv(id, email);

        if (rdv == null) {
            return new ModelAndView("redirect:/rdv");
        }

        return new ModelAndView("detailRdv", "rdv", rdv);
    }
}