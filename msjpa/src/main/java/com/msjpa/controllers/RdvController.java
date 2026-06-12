package com.msjpa.controllers;


import com.msjpa.models.RDV;
import com.msjpa.models.Rappels;
import com.msjpa.models.RdvDTO;
import com.msjpa.models.RdvPrestDTO;
import com.msjpa.repositories.RappelsRepository;
import com.msjpa.repositories.RdvRepository;
import com.msjpa.services.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rdvs")
public class RdvController {

    @Autowired
    private RdvService rdvService;
    @Autowired
    private RdvRepository rdvRepository;
    @Autowired
    private RappelsRepository rappelsRepository;

    @GetMapping
    public List<RdvDTO> getRdvByUser(@RequestParam String email) {
        return rdvService.getRdvByUser(email).stream()
                .map(rdvService::toDTO)
                .toList();
    }

    @PostMapping
    public RDV createRdv(@RequestBody RdvPrestDTO dto) {
        return rdvService.createRdv(dto);
    }

    @GetMapping("/{id}")
    public RdvDTO getRdv(@PathVariable Integer id, @RequestParam String email) {
        return rdvService.toDTO(rdvService.getRdvById(id, email));
    }

    @PutMapping("/{id}")
    public RDV updateRdv(@PathVariable Integer id,
                         @RequestBody RDV rdv,
                         @RequestParam String email) {
        return rdvService.updateRdv(id, rdv, email);
    }

    @DeleteMapping("/{id}")
    public void deleteRdv(@PathVariable Integer id,
                          @RequestParam String email) {
        rdvService.deleteRdv(id, email);
    }

    @PatchMapping("/{id}/valider")
    public RDV validerRdv(@PathVariable Integer id,
                          @RequestParam Integer statut,
                          @RequestParam String email) {

        RDV rdv = rdvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RDV introuvable : " + id));

        // Vérification sécurité : le rdv appartient bien à l'user connecté
        if (!rdv.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Accès non autorisé");
        }

        rdv.setIsOK(statut);  // 0 = réalisé, 3 = non réalisé
        return rdvRepository.save(rdv);
    }

    @PostMapping("/{id}/alertes")
    public Rappels addAlerte(@PathVariable Integer id,
                             @RequestParam String typeAlerte,
                             @RequestParam Integer delai,
                             @RequestParam String email) {

        RDV rdv = rdvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RDV introuvable : " + id));

        if (!rdv.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Accès non autorisé");
        }

        Rappels rappel = new Rappels();
        rappel.setRdv(rdv);
        rappel.setTypeAlerte(typeAlerte);
        rappel.setDelai(delai);

        return rappelsRepository.save(rappel);
    }
}