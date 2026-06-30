package com.msprof.Services;

import com.msprof.Mapper.PrestataireMapper;
import com.msprof.Models.PrestataireDTO;
import com.msprof.Models.PrestataireRequestDTO;
import com.msprof.Models.PrestataireUpdateDTO;
import com.msprof.Models.PrestataireDetailDTO;
import com.msprof.Models.PrestataireResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestataireService {

    private final MsJpaClient client;

    public PrestataireService(MsJpaClient client) {
        this.client = client;
    }

    public List<PrestataireResponseDTO> findAll() {
        return client.findAllPrestataires();
    }

    public PrestataireResponseDTO findById(Integer id) {
        return client.findPrestataireById(id);
    }

    public PrestataireDetailDTO findDetailById(Integer id) {
        return client.findPrestataireDetailById(id);
    }

    public void create(PrestataireRequestDTO request) {
        PrestataireDTO dto = PrestataireMapper.toJpa(request);
        client.createPrestataire(dto);
    }

    // ⚠️ Vérifier PrestataireMapper.toJpa() : si elle ne sait construire
    // qu'un PrestataireDTO (pas un PrestataireUpdateDTO), il faudra soit
    // une méthode toUpdateJpa() dédiée, soit accepter directement
    // un PrestataireUpdateDTO ici en entrée (voir remarque ci-dessous).
    public void update(Integer id, PrestataireRequestDTO request) {
        PrestataireUpdateDTO dto = PrestataireMapper.toJpaUpdate(request);
        client.updatePrestataire(id, dto);
    }

    public void delete(Integer id) {
        client.deletePrestataire(id);
    }
}