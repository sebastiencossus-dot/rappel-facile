package com.msrappel.Services;

import com.msrappel.Models.RappelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "msjpa")
public interface MsJpaClient {

    @GetMapping("/rappels")
    List<RappelDTO> findAllRappels();
}