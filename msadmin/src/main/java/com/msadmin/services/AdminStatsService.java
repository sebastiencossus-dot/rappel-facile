package com.msadmin.services;

import com.msadmin.services.MsJpaClient;
import com.msadmin.services.MsProfClient;
import com.msadmin.models.AdminRdvDTO;
import com.msadmin.models.AdminUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminStatsService {

    private final MsJpaClient msJpaClient;
    private final MsProfClient msProfClient;



    @Cacheable("adminRecentRdv")
    public List<AdminRdvDTO> getRecentRdv() {
        return msJpaClient.getRecentRdv();
    }

    @Cacheable("adminRecentUsers")
    public List<AdminUserDTO> getRecentUsers() {
        return msJpaClient.getRecentUsers();
    }

    @Cacheable("adminStats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = msJpaClient.getAdminStats();

        // ✅ On remplace le 0L par le vrai nombre de prestataires via msprof
        long nbPrestataires = msProfClient.findAll().size();

        return Map.of(
                "nbUsers", stats.getOrDefault("nbUsers", 0L),
                "nbRdv", stats.getOrDefault("nbRdv", 0L),
                "nbAlertes", stats.getOrDefault("nbAlertes", 0L),
                "nbPrestataires", nbPrestataires // ✅ vrai nombre
        );
    }
}