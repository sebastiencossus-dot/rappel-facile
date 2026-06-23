package com.webapp.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

@FeignClient(name = "msstats")
public interface MsstatsClient {

    @PostMapping("/stats/connexion")
    void recordConnexion(@RequestBody Map<String, String> payload);

    @PostMapping("/stats/visite")
    void recordVisite(@RequestBody Map<String, String> payload);

    @PostMapping("/stats/rdv")
    void recordRdvStat(@RequestBody Map<String, Object> payload);
}