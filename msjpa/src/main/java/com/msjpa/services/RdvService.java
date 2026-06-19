package com.msjpa.services;



import com.msjpa.models.*;
import com.msjpa.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class RdvService {

        @Autowired
        private RdvRepository rdvRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PrestataireRepository prestataireRepository;
        @Autowired
        private ProfessionRepository professionRepository;
        @Autowired
        private AdresseRepository adresseRepository;
        @Autowired
        private RappelsRepository rappelsRepository;

        public RdvDTO toDTO(RDV rdv) {
            RdvDTO dto = new RdvDTO();
            dto.setId(rdv.getId());
            dto.setDateRdv(rdv.getDateRdv());
            dto.setIsOK(rdv.getIsOK());
            dto.setMotif(rdv.getMotif());

            if (rdv.getPrestataires() != null) {
                dto.setPrestataireId(rdv.getPrestataires().getId());
                dto.setPrestataireNom(rdv.getPrestataires().getNom());
                dto.setPrestatairePrenom(rdv.getPrestataires().getPrenom());
            }
            if (rdv.getProfessions() != null) {
                dto.setProfessionId(rdv.getProfessions().getId());
                dto.setProfessionNom(rdv.getProfessions().getNom());
            }
            if (rdv.getAdresses() != null) {
                dto.setAdresseId(rdv.getAdresses().getId());
                dto.setAdresseNumero(rdv.getAdresses().getNumero());
                dto.setAdresseRue(rdv.getAdresses().getRue());
                dto.setAdresseVille(rdv.getAdresses().getVille());
                dto.setAdresseCodePostal(rdv.getAdresses().getCodepostal());
            }
            if (rdv.getUser() != null) {
                dto.setUserId(rdv.getUser().getId());
            }
            if (rdv.getRappels() != null) {
                dto.setRappels(rdv.getRappels().stream()
                        .map(r -> new RappelDTO(r.getId(), r.getDelai(), r.getTypeAlerte()))
                        .toList());
            }
            return dto;
        }

        public List<RDV> getRdvByUser(String email) {
            return rdvRepository.findByUser_Email(email);
        }


        public RDV createRdv(RdvPrestDTO dto) {
            RDV rdv = new RDV();
            rdv.setDateRdv(dto.getDateRdv());
            rdv.setMotif(dto.getMotif());
            rdv.setIsOK(1);

            if (dto.getUserId() != null) {
                rdv.setUser(userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User introuvable")));
            }
            if (dto.getPrestataireId() != null) {
                rdv.setPrestataires(prestataireRepository.findById(dto.getPrestataireId())
                        .orElseThrow(() -> new RuntimeException("Prestataire introuvable")));
            }
            if (dto.getAdresseId() != null) {
                rdv.setAdresses(adresseRepository.findById(dto.getAdresseId())
                        .orElseThrow(() -> new RuntimeException("Adresse introuvable")));
            }
            if (dto.getProfessionId() != null) {
                rdv.setProfessions(professionRepository.findById(dto.getProfessionId())
                        .orElseThrow(() -> new RuntimeException("Profession introuvable")));
            }

            RDV savedRdv = rdvRepository.save(rdv);

            Rappels rappel = new Rappels();
            rappel.setRdv(savedRdv);
            rappel.setTypeAlerte("EMAIL");
            rappel.setDelai(1440);
            rappelsRepository.save(rappel);

            return savedRdv;
        }


        public RDV updateRdv(Integer id, RDV rdv, String email) {

            RDV existing = rdvRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("RDV not found"));


            if (!existing.getUser().getEmail().equals(email)) {
                throw new RuntimeException("Unauthorized");
            }


            rdv.setId(id);
            rdv.setUser(existing.getUser());

            return rdvRepository.save(rdv);
        }


        public void deleteRdv(Integer id, String email) {

            RDV existing = rdvRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("RDV not found"));


            if (!existing.getUser().getEmail().equals(email)) {
                throw new RuntimeException("Unauthorized");
            }

            rdvRepository.delete(existing);
        }


        public RDV getRdvById(Integer id, String email) {

            RDV rdv = rdvRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("RDV not found"));

            if (!rdv.getUser().getEmail().equals(email)) {
                throw new RuntimeException("Unauthorized");
            }

            return rdv;
        }


    }

