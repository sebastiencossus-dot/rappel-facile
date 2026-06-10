package com.msprof.Services;

import com.msprof.Models.PrestataireResponseDTO;
import com.msprof.Models.PrestataireDTO;
import com.msprof.Models.Prestataires;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msjpa", contextId = "MsjpaClient")
public interface MsJpaClient {

    @PostMapping("/prestataires")
    void createPrestataire(@RequestBody PrestataireDTO dto);

    @GetMapping("/prestataires")
    List<PrestataireResponseDTO> findAllPrestataires();

    @PutMapping("/prestataires/{id}")
    void updatePrestataire(@PathVariable Integer id,
                           @RequestBody PrestataireDTO dto);

    @DeleteMapping("/prestataires/{id}")
    void deletePrestataire(@PathVariable Integer id);

    @GetMapping("/prestataires/{id}")
    PrestataireResponseDTO findPrestataireById(@PathVariable("id") Integer id);
}
