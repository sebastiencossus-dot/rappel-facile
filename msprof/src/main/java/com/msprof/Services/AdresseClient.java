package com.msprof.Services;



import com.msprof.Models.AdresseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "msjpa", contextId = "AdresseClient")
public interface AdresseClient {

    @GetMapping("/adresses")
    List<AdresseDTO> findAll();
}