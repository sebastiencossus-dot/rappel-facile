package com.webapp.services;

import com.webapp.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

// ✅ PrestataireService.java — épuré
@Service
@RequiredArgsConstructor
public class PrestataireService {

    private final PrestataireClient client;
    // MsJpaClient supprimé — plus nécessaire ici

    @Cacheable("prestataires")
    public List<PrestataireResponseDTO> findAll() {
        return client.findAll();
    }

    public PrestataireDetailDTO findById(Integer id) {
        return client.findById(id);
    }

    @CacheEvict(value = "prestataires", allEntries = true)
    public void create(PrestataireDTO dto) {
        client.create(dto);
    }

    @CacheEvict(value = "prestataires", allEntries = true)
    public void update(Integer id, PrestataireUpdateDTO dto) {
        client.update(id, dto);
    }

    @CacheEvict(value = "prestataires", allEntries = true)
    public void delete(Integer id) {
        client.delete(id);
    }
}
