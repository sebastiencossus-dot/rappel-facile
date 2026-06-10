package com.webapp.services;

import com.webapp.models.RDV;
import com.webapp.models.RdvPrestDTO;
import com.webapp.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msjpa")
public interface MsJpaClient {

    // USERS
    @GetMapping("/users")
    User findUserByEmail(@RequestParam String email);

    @PostMapping("/users")
    User createUser(@RequestBody User user);

    // RDV
    @GetMapping("/rdvs")
    List<RDV> getRdvByUser(@RequestParam("email") String email);

    @GetMapping("/rdvs/{id}")
    RDV getRdv(@PathVariable("id") Integer id,
               @RequestParam("email") String email);

    @PostMapping("/rdvs")
    RDV createRdv(@RequestBody RdvPrestDTO rdv);

    @PutMapping("/rdvs/{id}")
    RDV updateRdv(@PathVariable("id") Integer id,
                  @RequestBody RDV rdv,
                  @RequestParam("email") String email);

    @DeleteMapping("/rdvs/{id}")
    void deleteRdv(@PathVariable("id") Integer id,
                   @RequestParam("email") String email);
}