package com.webapp.controllers;

import com.webapp.models.PrestataireResponseDTO;
import com.webapp.services.AdminPrestataireService;
import com.webapp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/prestataires")
@RequiredArgsConstructor
public class AdminPrestataireController {

    private final AdminPrestataireService adminPrestataireService;
    private final SessionService sessionService;

    @GetMapping
    public String listPrestataires(Model model) {
        List<PrestataireResponseDTO> prestataires = adminPrestataireService.findAll();
        model.addAttribute("prestataires", prestataires);
        model.addAttribute("currentUser", sessionService.sessionUser());
        return "admin-prestataires";
    }

    @GetMapping("/{id}")
    public String detailPrestataire(@PathVariable Integer id, Model model) {
        PrestataireResponseDTO prestataire = adminPrestataireService.findById(id);
        model.addAttribute("prestataire", prestataire);
        model.addAttribute("currentUser", sessionService.sessionUser());
        return "admin-prestataire-detail";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        try {
            adminPrestataireService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Prestataire supprimé avec succès");
        } catch (Exception e) {
            // ✅ Afficher le message d'erreur retourné par msadmin/msjpa
            redirectAttributes.addFlashAttribute("error",
                    "Suppression impossible : ce prestataire a des RDV associés");
        }
        return "redirect:/admin/prestataires";
    }
}