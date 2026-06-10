package com.webapp.services;

import com.webapp.models.PrestataireDTO;
import com.webapp.models.PrestataireResponseDTO;
import com.webapp.models.Prestataires;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msprof", contextId = "prestataireClient")
public interface PrestataireClient {

    @GetMapping("/prestataires")
    List<PrestataireResponseDTO> findAll();

    @GetMapping("/prestataires/{id}")
    Prestataires findById(@PathVariable Integer id);

    @PostMapping("/prestataires")
    void create(@RequestBody PrestataireDTO dto);

    @PutMapping("/prestataires/{id}")
    void update(@PathVariable Integer id, @RequestBody PrestataireDTO dto);

    @DeleteMapping("/prestataires/{id}")
    void delete(@PathVariable Integer id);
}