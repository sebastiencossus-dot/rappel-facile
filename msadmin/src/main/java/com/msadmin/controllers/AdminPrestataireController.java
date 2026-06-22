package com.msadmin.controllers;

import com.msadmin.models.PrestataireResponseDTO;
import com.msadmin.services.AdminPrestataireService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/prestataires")
@RequiredArgsConstructor
public class AdminPrestataireController {

    private final AdminPrestataireService adminPrestataireService;

    @GetMapping
    public List<PrestataireResponseDTO> findAll() {
        return adminPrestataireService.findAll();
    }

    @GetMapping("/{id}")
    public PrestataireResponseDTO findById(@PathVariable Integer id) {
        return adminPrestataireService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        adminPrestataireService.delete(id);
    }
}