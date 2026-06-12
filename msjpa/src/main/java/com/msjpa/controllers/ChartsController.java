package com.msjpa.controllers;

import com.msjpa.services.ChartsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChartsController {

    private final ChartsService chartsService;

    public ChartsController(ChartsService chartsService) {
        this.chartsService = chartsService;
    }

    @GetMapping("/rdv/stats")
    public ResponseEntity<Map<String, List<Integer>>> getRdvStats(
            @RequestParam Integer idUser,
            @RequestParam int annee) {

        return ResponseEntity.ok(chartsService.getStats(idUser, annee));
    }
}