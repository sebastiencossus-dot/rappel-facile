package com.webapp.controllers;

import com.webapp.models.*;
import com.webapp.services.*;
import com.webapp.services.form.AlerteForm;
import com.webapp.services.form.rdvForm;
import com.webapp.services.mapper.RdvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

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

        User user = sessionService.sessionUser();
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }

        mav.addObject("currentUser", user);
        LocalDate today = LocalDate.now();
        List<RDV> rdvs = rdvService.getRdvByUser(user.getEmail());

        List<RDV> todayList = rdvs.stream()
                .filter(r -> r.getDateRdv() != null && r.getDateRdv().toLocalDate().equals(today))
                .sorted(Comparator.comparing(RDV::getDateRdv))
                .toList();

        List<RDV> attente = rdvs.stream()
                .filter(r -> Integer.valueOf(1).equals(r.getIsOK())
                        && r.getDateRdv() != null
                        && r.getDateRdv().toLocalDate().isBefore(today))
                .sorted(Comparator.comparing(RDV::getDateRdv))
                .toList();

        List<RDV> autres = rdvs.stream()
                .filter(r -> r.getDateRdv() != null && r.getDateRdv().toLocalDate().isAfter(today))
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
        String email = sessionService.sessionUser().getEmail();
        RDV rdv = msJpaClient.getRdv(id, email);
        if (rdv == null) return new ModelAndView("redirect:/rdv");

        rdvForm form = RdvMapper.toForm(rdv);

        ModelAndView mav = new ModelAndView("edit-rdv");
        mav.addObject("rdv", rdv);
        mav.addObject("rdvForm", form);
        mav.addObject("prestataires", prestataireService.findAll());
        mav.addObject("professions", referentielService.getAllProfessions());
        mav.addObject("adresses", referentielService.getAllAdresses());
        return mav;
    }

    @PostMapping("/rdv/update/{id}")
    public String updateRdv(@PathVariable Integer id, rdvForm form) {
        String email = sessionService.sessionUser().getEmail();
        RDV existing = msJpaClient.getRdv(id, email);
        if (existing == null) return "redirect:/rdv";

        existing.setDateRdv(form.getDateRdv());
        existing.setMotif(form.getMotif());
        existing.setPrestataireId(form.getPrestataireId());
        existing.setAdresseId(form.getAdresseId());
        existing.setProfessionId(form.getProfessionId());
        // isOK, userId, etc. restent inchangés car on repart de l'objet existant

        rdvService.updateRdv(id, existing);
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

    @GetMapping("/rdv/{id}/alertes")
    public ModelAndView listeAlertes(@PathVariable Integer id) {
        String email = sessionService.sessionUser().getEmail();
        RDV rdv = msJpaClient.getRdv(id, email);
        List<RappelDTO> rappels = rdvService.getRappelsByRdv(id);

        ModelAndView mav = new ModelAndView("listeAlertes");
        mav.addObject("rdv", rdv);
        mav.addObject("rappels", rappels);
        return mav;
    }

    @GetMapping("/rdv/{rdvId}/alertes/delete/{rappelId}")
    public String deleteAlerte(@PathVariable Integer rdvId,
                               @PathVariable Integer rappelId) {
        rdvService.deleteRappel(rappelId);
        return "redirect:/rdv/" + rdvId + "/alertes";
    }

    @GetMapping("/rdv/prestataire/{id}/details")
    @ResponseBody
    public Map<String, Object> getPrestataireDetails(@PathVariable Integer id) {


        List<Map<String, Object>> professions = new ArrayList<>();
        // Les professions viennent de msprof via PrestataireResponseDTO
        PrestataireResponseDTO dto = prestataireService.findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        List<Map<String, Object>> adresses = new ArrayList<>();

        if (dto != null) {
            // Professions — on utilise les professions du référentiel filtrées par nom
            List<Professions> toutesLesProfessions = referentielService.getAllProfessions();
            dto.getProfessions().forEach(profNom -> {
                toutesLesProfessions.stream()
                        .filter(p -> p.getNom().equals(profNom))
                        .findFirst()
                        .ifPresent(p -> {
                            Map<String, Object> profMap = new HashMap<>();
                            profMap.put("id", p.getId());
                            profMap.put("nom", p.getNom());
                            professions.add(profMap);
                        });
            });

            // Adresses
            dto.getAdresses().forEach(adr -> {
                Map<String, Object> adrMap = new HashMap<>();
                adrMap.put("id", adr.getId());
                adrMap.put("numero", adr.getNumero());
                adrMap.put("rue", adr.getRue());
                adrMap.put("ville", adr.getVille());
                adresses.add(adrMap);
            });
        }

        return Map.of("professions", professions, "adresses", adresses);
    }
}