package com.msjpa.services;



import com.msjpa.Mapper.PrestataireMapper;
import com.msjpa.models.*;
import com.msjpa.repositories.AdresseRepository;
import com.msjpa.repositories.PrestataireRepository;
import com.msjpa.repositories.ProfessionRepository;
import com.msjpa.models.AdresseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestataireService {

    private final PrestataireRepository prestataireRepository;
    private final ProfessionRepository professionRepository;

    public PrestataireService(PrestataireRepository prestataireRepository,
                              ProfessionRepository professionRepository) {
        this.prestataireRepository = prestataireRepository;
        this.professionRepository = professionRepository;
    }

    @Autowired
    private AdresseRepository adresseRepository; // à injecter

    @Transactional
    public void create(PrestataireDTO dto) {

        System.out.println("=== DTO reçu dans msjpa ===");
        System.out.println("nom: " + dto.getNom());
        System.out.println("prenom: " + dto.getPrenom());
        System.out.println("professionIds: " + dto.getProfessionIds());
        System.out.println("adresses: " + dto.getAdresses());


        Prestataires p = new Prestataires();
        p.setNom(dto.getNom());
        p.setPrenom(dto.getPrenom());
        p.setIsValide(true);
        p.setExercices(new ArrayList<>());
        p.setLocals(new ArrayList<>());
        prestataireRepository.save(p);

        // 2. Créer les Exerce (prestataire ↔ profession)
        if (dto.getProfessionIds() != null) {
            for (Integer idProf : dto.getProfessionIds()) {

                Professions prof = professionRepository.findById(idProf)
                        .orElseThrow(() -> new RuntimeException("Profession introuvable : " + idProf));

                // Initialiser la clé composite
                ExerceId exerceId = new ExerceId();
                exerceId.setPrestatairesId(p.getId());
                exerceId.setProfessionsId(idProf);

                Exerce ex = new Exerce();
                ex.setId(exerceId);           // ← clé composite initialisée
                ex.setPrestataires(p);
                ex.setProfessions(prof);      // ← setter correct (pas setProfession)
                ex.setValide(true);

                p.getExercices().add(ex);
            }
        }

        // 3. Créer les adresses puis les Local (prestataire ↔ adresse)
        if (dto.getAdresses() != null) {
            for (AdresseDTO a : dto.getAdresses()) {

                // Sauvegarder l'adresse d'abord pour avoir son id
                Adresses adr = new Adresses();
                adr.setRue(a.getRue());
                adr.setNumero(a.getNumero());
                adr.setVille(a.getVille());
                adr.setCodepostal(a.getCodepostal());
                adresseRepository.save(adr);  // ← save pour avoir l'id

                // Initialiser la clé composite
                LocalId localId = new LocalId();
                localId.setPrestatairesId(p.getId());
                localId.setAdressesId(adr.getId());

                Local l = new Local();
                l.setId(localId);             // ← clé composite initialisée
                l.setPrestataires(p);
                l.setAdresses(adr);           // ← setter correct (pas setAdresse)
                l.setValide(true);

                p.getLocals().add(l);
            }
        }

        // 4. Sauvegarder avec toutes les relations
        prestataireRepository.save(p);
    }

    public List<PrestataireResponseDTO> getAll() {

        List<Prestataires> prestataires =
                prestataireRepository.findAllWithExercices();
                prestataireRepository.findAllWithLocals();

        return prestataires.stream()
                .map(PrestataireMapper::toDTO)
                .toList();
    }

    @Transactional
    public void update(Integer id, PrestataireUpdateDTO dto) {

        Prestataires p = prestataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        p.setNom(dto.getNom());
        p.setPrenom(dto.getPrenom());


        p.getExercices().clear();

        List<Exerce> newExercices = new ArrayList<>();

        for (Integer profId : dto.getProfessionIds()) {

            Exerce ex = new Exerce();
            ex.setPrestataires(p);

            Professions prof = new Professions();
            prof.setId(profId);

            ex.setProfessions(prof);

            newExercices.add(ex);
        }

        p.getExercices().addAll(newExercices);


        p.getLocals().clear();

        List<Local> newLocals = new ArrayList<>();

        for (AdresseDTO a : dto.getAdresses()) {

            Local l = new Local();
            l.setPrestataires(p);

            Adresses adr = new Adresses();
            adr.setRue(a.getRue());
            adr.setNumero(a.getNumero());
            adr.setVille(a.getVille());
            adr.setCodepostal(a.getCodepostal());

            l.setAdresses(adr);

            newLocals.add(l);
        }

        p.getLocals().addAll(newLocals);

        prestataireRepository.save(p);
    }

    @Transactional
    public void delete(Integer id) {

        Prestataires p = prestataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        p.getExercices().clear();
        p.getLocals().clear();

        prestataireRepository.delete(p);
    }

    public PrestataireResponseDTO getById(Integer id) {
        return null;
    }
}