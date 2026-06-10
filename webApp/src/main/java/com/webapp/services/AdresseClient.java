package com.webapp.services;

import com.webapp.models.AdresseDTO;
import com.webapp.models.Adresses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msjpa", contextId = "adresseClient")
public interface AdresseClient {

    @GetMapping("/adresses")
    List<AdresseDTO> findAll();

    @GetMapping("/adresses/{id}")
    Adresses findById(@PathVariable Integer id);
}