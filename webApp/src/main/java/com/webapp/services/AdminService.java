package com.webapp.services;

import com.webapp.models.AdminRdvDTO;
import com.webapp.models.AdminUserDTO;
import com.webapp.models.DashboardStatsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MsAdminClient msAdminClient; // ✅ remplace msJpaClient

    public Map<String, Long> getStats() {
        return msAdminClient.getAdminStats();
    }

    public List<AdminRdvDTO> getRecentRdv() {
        return msAdminClient.getRecentRdv();
    }

    public List<AdminUserDTO> getRecentUsers() {
        return msAdminClient.getRecentUsers();
    }



    // Dans AdminService
    public DashboardStatsDTO getMongoStats() {
        return msAdminClient.getMongoStats();
    }
}