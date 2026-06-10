package com.webapp.services;

import com.webapp.models.Professions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msjpa", contextId = "professionClient")
public interface ProfessionClient {

    @GetMapping("/professions")
    List<Professions> findAll();

    @GetMapping("/professions/{id}")
    Professions findById(@PathVariable Integer id);
}
