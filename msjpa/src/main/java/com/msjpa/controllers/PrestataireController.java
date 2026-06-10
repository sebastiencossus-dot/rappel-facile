package com.msjpa.controllers;


import com.msjpa.models.*;
import com.msjpa.repositories.AdresseRepository;
import com.msjpa.repositories.PrestataireRepository;
import com.msjpa.repositories.ProfessionRepository;
import com.msjpa.services.PrestataireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/prestataires")
public class PrestataireController {

    @Autowired
    private PrestataireRepository prestataireRepository;
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private AdresseRepository adresseRepository;
    private final PrestataireService service;

    public PrestataireController(PrestataireService service) {
        this.service = service;
    }

    @GetMapping
    public List<PrestataireResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PrestataireResponseDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    @Transactional
    public void create(@RequestBody PrestataireDTO dto) {


        Prestataires p = new Prestataires();
        p.setNom(dto.getNom());
        p.setPrenom(dto.getPrenom());
        p.setIsValide(true);
        p.setExercices(new ArrayList<>());
        p.setLocals(new ArrayList<>());

        // Sauvegarder d'abord le prestataire pour avoir son id
        prestataireRepository.save(p);
        prestataireRepository.flush();

        // Ensuite ajouter les professions
        if (dto.getProfessionIds() != null) {
            for (Integer idProf : dto.getProfessionIds()) {
                // Recharger la profession depuis la BDD (évite l'objet transient)
                Professions prof = professionRepository.findById(idProf)
                        .orElseThrow(() -> new RuntimeException("Profession introuvable : " + idProf));

                ExerceId exerceId = new ExerceId();
                exerceId.setPrestatairesId(p.getId());
                exerceId.setProfessionsId(idProf);

                Exerce ex = new Exerce();
                ex.setId(exerceId);        // ← clé composite initialisée
                ex.setPrestataires(p);
                ex.setProfessions(prof);
                ex.setValide(true);
                p.getExercices().add(ex);
            }
        }

        if (dto.getAdresses() != null) {
            for (AdresseDTO a : dto.getAdresses()) {

                // ← ignorer les adresses vides
                if (a.getRue() == null && a.getVille() == null
                        && a.getNumero() == null && a.getCodepostal() == null) {
                    continue;
                }

                Adresses adr;

                if (a.getId() != null) {
                    adr = adresseRepository.findById(a.getId())
                            .orElseThrow(() -> new RuntimeException("Adresse introuvable : " + a.getId()));
                } else {
                    adr = new Adresses();
                    adr.setRue(a.getRue());
                    adr.setNumero(a.getNumero());
                    adr.setVille(a.getVille());
                    adr.setCodepostal(a.getCodepostal());
                    adresseRepository.saveAndFlush(adr);
                }

                LocalId localId = new LocalId();
                localId.setPrestatairesId(p.getId());
                localId.setAdressesId(adr.getId());

                Local l = new Local();
                l.setId(localId);
                l.setPrestataires(p);
                l.setAdresses(adr);
                l.setValide(true);
                p.getLocals().add(l);
            }
        }

        prestataireRepository.save(p);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id,
                       @RequestBody PrestataireUpdateDTO dto) {
        service.update(id, dto);
    }
}