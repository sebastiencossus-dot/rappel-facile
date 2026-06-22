package com.msadmin.services;

import com.msadmin.models.PrestataireResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msprof")
public interface MsProfClient {

    @GetMapping("/prestataires")
    List<PrestataireResponseDTO> findAll();

    @GetMapping("/prestataires/{id}")
    PrestataireResponseDTO findById(@PathVariable Integer id);

    @DeleteMapping("/prestataires/{id}")
    void delete(@PathVariable Integer id);
}