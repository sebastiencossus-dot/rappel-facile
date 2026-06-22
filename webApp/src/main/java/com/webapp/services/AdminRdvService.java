package com.webapp.services;

import com.webapp.models.AdminRdvDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRdvService {

    private final MsAdminClient msAdminClient;

    public List<AdminRdvDTO> findAll() {
        return msAdminClient.findAllRdv();
    }

    public List<AdminRdvDTO> findByStatut(Integer statut) {
        return msAdminClient.findRdvByStatut(statut);
    }

    public void delete(Integer id) {
        msAdminClient.deleteRdv(id);
    }
}