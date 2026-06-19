package com.msadmin.services;

import com.msadmin.clients.MsJpaClient;
import com.msadmin.models.AdminRdvDTO;
import com.msadmin.models.AdminUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminStatsService {

    private final MsJpaClient msJpaClient;

    @Cacheable("adminStats")
    public Map<String, Long> getStats() {
        return msJpaClient.getAdminStats();
    }

    @Cacheable("adminRecentRdv")
    public List<AdminRdvDTO> getRecentRdv() {
        return msJpaClient.getRecentRdv();
    }

    @Cacheable("adminRecentUsers")
    public List<AdminUserDTO> getRecentUsers() {
        return msJpaClient.getRecentUsers();
    }
}