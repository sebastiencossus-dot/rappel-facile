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

        Prestataires p = new Prestataires();
        p.setNom(dto.getNom());
        p.setPrenom(dto.getPrenom());
        p.setIsValide(true);
        p.setExercices(new ArrayList<>());
        p.setLocals(new ArrayList<>());
        prestataireRepository.save(p);

        if (dto.getProfessionIds() != null) {
            for (Integer idProf : dto.getProfessionIds()) {

                Professions prof = professionRepository.findById(idProf)
                        .orElseThrow(() -> new RuntimeException("Profession introuvable : " + idProf));

                ExerceId exerceId = new ExerceId();
                exerceId.setPrestatairesId(p.getId());
                exerceId.setProfessionsId(idProf);

                Exerce ex = new Exerce();
                ex.setId(exerceId);
                ex.setPrestataires(p);
                ex.setProfessions(prof);
                ex.setValide(true);

                p.getExercices().add(ex);
            }
        }

        if (dto.getAdresses() != null) {
            for (AdresseDTO a : dto.getAdresses()) {

                Adresses adr = new Adresses();
                adr.setRue(a.getRue());
                adr.setNumero(a.getNumero());
                adr.setVille(a.getVille());
                adr.setCodepostal(a.getCodepostal());
                adresseRepository.save(adr);

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

    public List<PrestataireResponseDTO> getAll() {

        List<Prestataires> prestataires = prestataireRepository.findAllWithExercices();

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

        syncExercices(p, dto.getProfessionIds());
        syncLocals(p, dto.getAdresses());

        prestataireRepository.save(p);
    }

    /**
     * Retire les professions qui ne sont plus sélectionnées et ajoute
     * celles qui manquent, en rechargeant Professions depuis la BDD
     * (objet géré par Hibernate, contrairement à new Professions() + setId()).
     */
    private void syncExercices(Prestataires p, List<Integer> professionIds) {

        java.util.Set<Integer> nouvellesIds = (professionIds != null)
                ? new java.util.HashSet<>(professionIds)
                : new java.util.HashSet<>();

        // nécessite orphanRemoval = true sur @OneToMany exercices dans Prestataires
        p.getExercices().removeIf(ex -> !nouvellesIds.contains(ex.getProfessions().getId()));

        java.util.Set<Integer> idsExistants = p.getExercices().stream()
                .map(ex -> ex.getProfessions().getId())
                .collect(java.util.stream.Collectors.toSet());

        for (Integer profId : nouvellesIds) {
            if (idsExistants.contains(profId)) {
                continue;
            }

            Professions prof = professionRepository.findById(profId)
                    .orElseThrow(() -> new RuntimeException("Profession introuvable : " + profId));

            ExerceId exerceId = new ExerceId();
            exerceId.setPrestatairesId(p.getId());
            exerceId.setProfessionsId(profId);

            Exerce ex = new Exerce();
            ex.setId(exerceId);
            ex.setPrestataires(p);
            ex.setProfessions(prof);
            ex.setValide(true);

            p.getExercices().add(ex);
        }
    }

    /**
     * Pour chaque adresse du DTO : réutilise l'adresse existante si un id
     * est fourni, sinon crée une nouvelle adresse en BDD avant de lier.
     * Retire ensuite les Local dont l'adresse n'est plus dans le DTO.
     */
    private void syncLocals(Prestataires p, List<AdresseDTO> adressesDTO) {

        if (adressesDTO == null) {
            adressesDTO = new ArrayList<>();
        }

        java.util.Set<Integer> adresseIdsVoulues = new java.util.HashSet<>();

        for (AdresseDTO a : adressesDTO) {

            boolean vide = a.getId() == null
                    && a.getRue() == null
                    && a.getNumero() == null
                    && a.getVille() == null
                    && a.getCodepostal() == null;
            if (vide) {
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

            adresseIdsVoulues.add(adr.getId());

            boolean dejaLie = p.getLocals().stream()
                    .anyMatch(l -> l.getAdresses().getId().equals(adr.getId()));

            if (!dejaLie) {
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

        // nécessite orphanRemoval = true sur @OneToMany locals dans Prestataires
        p.getLocals().removeIf(l -> !adresseIdsVoulues.contains(l.getAdresses().getId()));
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
        Prestataires p = prestataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire introuvable : " + id));
        return PrestataireMapper.toDTO(p);
    }

    /**
     * Version "détail" pour l'écran d'édition : renvoie les ids de
     * profession et les adresses liées à plat, sans relations circulaires.
     */
    public PrestataireDetailDTO getDetailById(Integer id) {
        Prestataires p = prestataireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestataire introuvable : " + id));
        return PrestataireMapper.toDetailDTO(p);
    }
}