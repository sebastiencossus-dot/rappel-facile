package com.msadmin.controllers;

import com.msadmin.models.AdminRdvDTO;
import com.msadmin.models.AdminUserDTO;
import com.msadmin.services.AdminStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
public class AdminStatsController {

    private final AdminStatsService adminStatsService;

    @GetMapping
    public Map<String, Long> getStats() {
        return adminStatsService.getStats();
    }

    @GetMapping("/recent-rdv")
    public List<AdminRdvDTO> getRecentRdv() {
        return adminStatsService.getRecentRdv();
    }

    @GetMapping("/recent-users")
    public List<AdminUserDTO> getRecentUsers() {
        return adminStatsService.getRecentUsers();
    }
}