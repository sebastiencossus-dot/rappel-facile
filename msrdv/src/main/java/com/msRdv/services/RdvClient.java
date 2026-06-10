package com.msRdv.services;

import com.msRdv.models.RDV;
import com.msRdv.models.RdvDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msjpa", url = "http://localhost:8091")
public interface RdvClient {

    @GetMapping("/rdv/user/{email}")
    List<RdvDTO> getByUser(@PathVariable String email);

    @PostMapping("/rdv")
    RdvDTO create(@RequestBody RDV rdv);

    @PutMapping("/rdv/{id}")
    RdvDTO update(@PathVariable Integer id, @RequestBody RDV rdv);

    @DeleteMapping("/rdv/{id}")
    void delete(@PathVariable Integer id);

    @GetMapping("/rdv/{id}")
    RdvDTO getById(@PathVariable Integer id);
}