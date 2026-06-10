package com.msprof.Services;

import com.msprof.Mapper.PrestataireMapper;
import com.msprof.Models.PrestataireDTO;
import com.msprof.Models.PrestataireRequestDTO;

import com.msprof.Models.PrestataireResponseDTO;
import com.msprof.Models.Prestataires;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestataireService {

    private final MsJpaClient client;

    public PrestataireService(MsJpaClient client) {
        this.client = client;
    }

    public List<PrestataireResponseDTO> findAll() {       // ← à ajouter
        return client.findAllPrestataires();
    }

    public PrestataireResponseDTO findById(Integer id) {  // ← à ajouter
        return client.findPrestataireById(id);
    }

    public void create(PrestataireRequestDTO request) {

        PrestataireDTO dto = PrestataireMapper.toJpa(request);

        client.createPrestataire(dto);
    }

    public void update(Integer id, PrestataireRequestDTO request) {
        PrestataireDTO dto = PrestataireMapper.toJpa(request);
        client.updatePrestataire(id, dto);
    }

    public void delete(Integer id) {
        client.deletePrestataire(id);
    }
}