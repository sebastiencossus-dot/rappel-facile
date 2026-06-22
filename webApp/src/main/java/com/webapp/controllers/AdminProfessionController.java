package com.webapp.controllers;

import com.webapp.models.CategorieDTO;
import com.webapp.models.ProfessionDTO;
import com.webapp.services.AdminProfessionService;
import com.webapp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/professions")
@RequiredArgsConstructor
public class AdminProfessionController {

    private final AdminProfessionService adminProfessionService;
    private final SessionService sessionService;

    @GetMapping
    public String list(Model model) {
        List<ProfessionDTO> professions = adminProfessionService.findAllProfessions();
        List<CategorieDTO> categories = adminProfessionService.findAllCategories();
        model.addAttribute("professions", professions);
        model.addAttribute("categories", categories);
        model.addAttribute("newProfession", new ProfessionDTO());
        model.addAttribute("newCategorie", new CategorieDTO());
        model.addAttribute("currentUser", sessionService.sessionUser());
        return "admin-professions";
    }

    @PostMapping("/add")
    public String addProfession(@ModelAttribute ProfessionDTO dto,
                                RedirectAttributes redirectAttributes) {
        try {
            adminProfessionService.createProfession(dto);
            redirectAttributes.addFlashAttribute("success", "Profession ajoutée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout");
        }
        return "redirect:/admin/professions";
    }

    @GetMapping("/{id}/delete")
    public String deleteProfession(@PathVariable Integer id,
                                   RedirectAttributes redirectAttributes) {
        try {
            adminProfessionService.deleteProfession(id);
            redirectAttributes.addFlashAttribute("success", "Profession supprimée");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Suppression impossible : profession liée à des RDV ou prestataires");
        }
        return "redirect:/admin/professions";
    }

    @PostMapping("/categories/add")
    public String addCategorie(@ModelAttribute CategorieDTO dto,
                               RedirectAttributes redirectAttributes) {
        try {
            adminProfessionService.createCategorie(dto);
            redirectAttributes.addFlashAttribute("success", "Catégorie ajoutée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout");
        }
        return "redirect:/admin/professions";
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteCategorie(@PathVariable Integer id,
                                  RedirectAttributes redirectAttributes) {
        try {
            adminProfessionService.deleteCategorie(id);
            redirectAttributes.addFlashAttribute("success", "Catégorie supprimée");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Suppression impossible : catégorie contient des professions");
        }
        return "redirect:/admin/professions";
    }
}