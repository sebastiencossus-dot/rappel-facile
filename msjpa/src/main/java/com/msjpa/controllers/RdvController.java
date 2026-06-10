package com.msjpa.controllers;


import com.msjpa.models.RDV;
import com.msjpa.models.RdvPrestDTO;
import com.msjpa.services.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rdvs")
public class RdvController {

    @Autowired
    private RdvService rdvService;

    @GetMapping
    public List<RDV> getRdvByUser(@RequestParam String email) {
        return rdvService.getRdvByUser(email);
    }

    @PostMapping
    public RDV createRdv(@RequestBody RdvPrestDTO dto) {
        return rdvService.createRdv(dto);
    }

    @GetMapping("/{id}")
    public RDV getRdv(@PathVariable Integer id, @RequestParam String email) {
        return rdvService.getRdvById(id, email);
    }

    @PutMapping("/{id}")
    public RDV updateRdv(@PathVariable Integer id,
                         @RequestBody RDV rdv,
                         @RequestParam String email) {
        return rdvService.updateRdv(id, rdv, email);
    }

    @DeleteMapping("/{id}")
    public void deleteRdv(@PathVariable Integer id,
                          @RequestParam String email) {
        rdvService.deleteRdv(id, email);
    }


}