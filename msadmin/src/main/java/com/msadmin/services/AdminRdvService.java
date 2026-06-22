package com.msadmin.services;

import com.msadmin.services.MsJpaClient;
import com.msadmin.models.AdminRdvDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminRdvService {

    private final MsJpaClient msJpaClient;

    public List<AdminRdvDTO> findAll() {
        return msJpaClient.getAllRdv();
    }

    public List<AdminRdvDTO> findByStatut(Integer statut) {
        return msJpaClient.getRdvByStatut(statut);
    }

    public void delete(Integer id) {
        msJpaClient.deleteRdvAdmin(id);
    }
}