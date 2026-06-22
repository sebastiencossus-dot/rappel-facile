package com.webapp.services;

import com.webapp.models.PrestataireResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPrestataireService {

    private final MsAdminClient msAdminClient;

    public List<PrestataireResponseDTO> findAll() {
        return msAdminClient.findAllPrestataires();
    }

    public PrestataireResponseDTO findById(Integer id) {
        return msAdminClient.findPrestataireById(id);
    }

    public void delete(Integer id) {
        msAdminClient.deletePrestataire(id);
    }
}