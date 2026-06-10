package com.msRdv.services;

import com.msRdv.models.RDV;
import com.msRdv.models.RdvDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RdvService {

    @Autowired
    private RdvClient rdvClient;

    public List<RdvDTO> getRdvByUser(String email) {
        return rdvClient.getByUser(email);
    }

    public RdvDTO createRdv(RDV rdv) {
        return rdvClient.create(rdv);
    }

    public RdvDTO updateRdv(Integer id, RDV rdv, String email) {
        // sécurité métier côté msRdv (optionnel)
        RdvDTO existing = rdvClient.getById(id);



        return rdvClient.update(id, rdv);
    }

    public void deleteRdv(Integer id, String email) {
        RdvDTO existing = rdvClient.getById(id);



        rdvClient.delete(id);
    }

    public RdvDTO getRdvById(Integer id, String email) {
        RdvDTO rdv = rdvClient.getById(id);



        return rdv;
    }
}