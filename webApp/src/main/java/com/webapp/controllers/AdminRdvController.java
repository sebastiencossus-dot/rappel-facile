package com.webapp.controllers;

import com.webapp.models.AdminRdvDTO;
import com.webapp.services.AdminRdvService;
import com.webapp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/rdv")
@RequiredArgsConstructor
public class AdminRdvController {

    private final AdminRdvService adminRdvService;
    private final SessionService sessionService;

    @GetMapping
    public String listRdv(@RequestParam(required = false) Integer statut, Model model) {
        List<AdminRdvDTO> rdvs;

        if (statut != null) {
            rdvs = adminRdvService.findByStatut(statut);
        } else {
            rdvs = adminRdvService.findAll();
        }

        model.addAttribute("rdvs", rdvs);
        model.addAttribute("statutFiltre", statut);
        model.addAttribute("currentUser", sessionService.sessionUser());
        return "admin-rdv";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        try {
            adminRdvService.delete(id);
            redirectAttributes.addFlashAttribute("success", "RDV supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        return "redirect:/admin/rdv";
    }
}