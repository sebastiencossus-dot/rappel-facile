package com.webapp.controllers;

import com.webapp.models.RDV;
import com.webapp.models.User;
import com.webapp.services.*;
import com.webapp.services.form.AlerteForm;
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

    @Autowired RdvService rdvService;
    @Autowired SessionService sessionService;
    @Autowired MsJpaClient msJpaClient;
    @Autowired ReferentielService referentielService;
    @Autowired PrestataireService prestataireService;

    @GetMapping("/rdv")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView();

        User user = sessionService.sessionUser(); // ✅ sans HttpSession
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }

        mav.addObject("currentUser", user);
        LocalDate today = LocalDate.now();
        List<RDV> rdvs = rdvService.getRdvByUser(user.getEmail());

        List<RDV> todayList = rdvs.stream()
                .filter(r -> r.getDateRdv().toLocalDate().equals(today))
                .sorted(Comparator.comparing(RDV::getDateRdv))
                .toList();

        List<RDV> attente = rdvs.stream()
                .filter(r -> r.getIsOK() == 1 && r.getDateRdv().toLocalDate().isBefore(today))
                .sorted(Comparator.comparing(RDV::getDateRdv))
                .toList();

        List<RDV> autres = rdvs.stream()
                .filter(r -> r.getDateRdv().toLocalDate().isAfter(today))
                .sorted(Comparator.comparing(RDV::getDateRdv))
                .toList();

        mav.addObject("rdvJour", todayList);
        mav.addObject("rdvAttente", attente);
        mav.addObject("rdvAutres", autres);
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/rdv/add")
    public String createForm(Model model) {
        model.addAttribute("rdvForm", new rdvForm());
        model.addAttribute("prestataires", prestataireService.findAll());
        model.addAttribute("professions", referentielService.getAllProfessions());
        model.addAttribute("adresses", referentielService.getAllAdresses());
        return "edit-rdv";
    }

    @PostMapping("/rdv/add")
    public String createRdv(@ModelAttribute rdvForm form) {
        rdvService.createRdv(form);
        return "redirect:/rdv";
    }

    @GetMapping("/rdv/edit/{id}")
    public ModelAndView editRdv(@PathVariable Integer id) {
        String email = sessionService.sessionUser().getEmail(); // ✅ sans HttpSession
        RDV rdv = msJpaClient.getRdv(id, email);
        if (rdv == null) return new ModelAndView("redirect:/rdv");
        return new ModelAndView("rdv-form", "rdv", rdv);
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

    @GetMapping("/rdv/{id}")
    public ModelAndView detailRdv(@PathVariable Integer id) {
        String email = sessionService.sessionUser().getEmail(); // ✅ sans HttpSession
        RDV rdv = msJpaClient.getRdv(id, email);
        if (rdv == null) return new ModelAndView("redirect:/rdv");
        return new ModelAndView("detailRdv", "rdv", rdv);
    }

    @PostMapping("/rdv/{id}/valider")
    public String validerRdv(@PathVariable Integer id,
                             @RequestParam Integer statut) {
        User user = sessionService.sessionUser(); // ✅ sans HttpSession
        rdvService.validerRdv(id, statut, user.getEmail());
        return "redirect:/rdv"; // ✅ corrigé : redirect:/rdv et non redirect:/
    }

    @GetMapping("/rdv/{id}/alerte/add")
    public String addAlerteForm(@PathVariable Integer id, Model model) {
        model.addAttribute("rdvId", id);
        model.addAttribute("alerteForm", new AlerteForm());
        return "addalert";
    }

    @PostMapping("/rdv/{id}/alerte/add")
    public String addAlerte(@PathVariable Integer id,
                            @ModelAttribute AlerteForm form) {
        String email = sessionService.sessionUser().getEmail();
        rdvService.addAlerte(id, form, email);
        return "redirect:/rdv/" + id;
    }

}