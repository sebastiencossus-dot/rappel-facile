package com.msadmin.services;

import com.msadmin.services.MsProfClient;
import com.msadmin.models.PrestataireResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPrestataireService {

    private final MsProfClient msProfClient;

    @Cacheable("adminPrestataires")
    public List<PrestataireResponseDTO> findAll() {
        return msProfClient.findAll();
    }

    public PrestataireResponseDTO findById(Integer id) {
        return msProfClient.findById(id);
    }

    @CacheEvict(value = "adminPrestataires", allEntries = true)
    public void delete(Integer id) {
        msProfClient.delete(id);
    }
}