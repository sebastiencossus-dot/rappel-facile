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

            return rdvRepository.save(rdv);
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

