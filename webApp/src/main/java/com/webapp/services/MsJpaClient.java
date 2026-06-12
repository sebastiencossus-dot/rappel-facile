package com.webapp.services;

import com.webapp.models.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/professions")
    List<Professions> findAllProfessions();

    @GetMapping("/categories")
    List<Categories> findAllCategories();

    @GetMapping("/adresses")
    List<Adresses> findAllAdresses();

    @PatchMapping("/rdvs/{id}/valider")
    void validerRdv(@PathVariable("id") Integer id,
                    @RequestParam("statut") Integer statut,
                    @RequestParam("email") String email);

    @PostMapping("/rdvs/{id}/alertes")
    void addAlerte(@PathVariable("id") Integer rdvId,
                   @RequestParam("typeAlerte") String typeAlerte,
                   @RequestParam("delai") Integer delai,
                   @RequestParam("email") String email);

    @GetMapping("/rdv/stats")
    Map<String, List<Integer>> getRdvStats(
            @RequestParam("idUser") Long idUser,
            @RequestParam("annee") int annee
    );
}