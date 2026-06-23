package com.msadmin.services;

import com.msadmin.models.DashboardStatsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msstats")
public interface MsstatsClient {

    @GetMapping("/stats/dashboard")
    DashboardStatsDTO getDashboard();
}