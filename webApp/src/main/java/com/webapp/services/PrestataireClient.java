package com.webapp.services;

import com.webapp.models.PrestataireDTO;
import com.webapp.models.PrestataireDetailDTO;
import com.webapp.models.PrestataireResponseDTO;
import com.webapp.models.PrestataireUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msprof", contextId = "prestataireClient")
public interface PrestataireClient {

    @GetMapping("/prestataires")
    List<PrestataireResponseDTO> findAll();

    @GetMapping("/prestataires/{id}/detail")
    PrestataireDetailDTO findById(@PathVariable Integer id);

    @PostMapping("/prestataires")
    void create(@RequestBody PrestataireDTO dto);

    @PutMapping("/prestataires/{id}")
    void update(@PathVariable Integer id, @RequestBody PrestataireUpdateDTO dto);

    @DeleteMapping("/prestataires/{id}")
    void delete(@PathVariable Integer id);
}