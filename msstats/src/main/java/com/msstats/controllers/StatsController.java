package com.msstats.controllers;

import com.msstats.models.*;
import com.msstats.services.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;


    @PostMapping("/connexion")
    public void recordConnexion(@RequestBody ConnexionRequest request) {
        statsService.recordConnexion(request);
    }


    @PostMapping("/visite")
    public void recordVisite(@RequestBody VisiteRequest request) {
        statsService.recordVisite(request);
    }


    @PostMapping("/rdv")
    public void recordRdvStat(@RequestBody RdvStatRequest request) {
        statsService.recordRdvStat(request);
    }


    @GetMapping("/dashboard")
    public DashboardStatsDTO getDashboard() {
        return statsService.getDashboard();
    }
}