package com.msRdv.controllers;


import com.msRdv.models.RDV;

import com.msRdv.models.RdvDTO;
import com.msRdv.services.AdresseService;
import com.msRdv.services.PrestataireService;
import com.msRdv.services.ProfessionService;
import com.msRdv.services.RdvService;


import com.msRdv.services.form.rdvForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rdvs")
public class RdvController {

    @Autowired
    private RdvService rdvService;

    @Autowired
    private PrestataireService prestataireService;

    @Autowired
    private ProfessionService professionService;

    @Autowired
    private AdresseService adresseService;

    @GetMapping
    public List<RdvDTO> getRdvByUser(@RequestParam String email) {
        return rdvService.getRdvByUser(email);
    }

    @PostMapping
    public RdvDTO createRdv(@ModelAttribute rdvForm form) {
        System.out.println("FORM RECU : " + form);

        return null;
    }

    @GetMapping("/{id}")
    public RdvDTO getRdv(@PathVariable Integer id, @RequestParam String email) {
        return rdvService.getRdvById(id, email);
    }

    @PutMapping("/{id}")
    public RdvDTO updateRdv(@PathVariable Integer id,
                         @RequestBody RDV rdv,
                         @RequestParam String email) {
        return rdvService.updateRdv(id, rdv, email);
    }

    @DeleteMapping("/{id}")
    public void deleteRdv(@PathVariable Integer id,
                          @RequestParam String email) {
        rdvService.deleteRdv(id, email);
    }

    @GetMapping("/rdv/form")
    public String showForm(Model model) {

        model.addAttribute("rdvForm", new rdvForm());
        model.addAttribute("prestataires", prestataireService.getAll());
        model.addAttribute("professions", professionService.getAll());
        model.addAttribute("adresses", adresseService.getAll());
        return "edit-rdv";
    }
}