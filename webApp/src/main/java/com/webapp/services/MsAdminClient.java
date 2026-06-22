package com.webapp.services;

import com.webapp.models.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msadmin")
public interface MsAdminClient {

    @GetMapping("/admin/users")
    List<User> findAllUsers();

    @GetMapping("/admin/users/{id}")
    User findUserById(@PathVariable("id") Integer id);

    @PutMapping("/admin/users/{id}")
    User updateUser(@PathVariable("id") Integer id, @RequestBody User user);

    @DeleteMapping("/admin/users/{id}")
    void deleteUser(@PathVariable("id") Integer id);

    @GetMapping("/admin/stats")
    Map<String, Long> getAdminStats();

    @GetMapping("/admin/stats/recent-rdv")
    List<AdminRdvDTO> getRecentRdv();

    @GetMapping("/admin/stats/recent-users")
    List<AdminUserDTO> getRecentUsers();


    @GetMapping("/admin/prestataires")
    List<PrestataireResponseDTO> findAllPrestataires();

    @GetMapping("/admin/prestataires/{id}")
    PrestataireResponseDTO findPrestataireById(@PathVariable("id") Integer id);

    @DeleteMapping("/admin/prestataires/{id}")
    void deletePrestataire(@PathVariable("id") Integer id);

    @GetMapping("/admin/rdv")
    List<AdminRdvDTO> findAllRdv();

    @GetMapping("/admin/rdv/statut/{statut}")
    List<AdminRdvDTO> findRdvByStatut(@PathVariable("statut") Integer statut);

    @DeleteMapping("/admin/rdv/{id}")
    void deleteRdv(@PathVariable("id") Integer id);

    @GetMapping("/admin/professions")
    List<ProfessionDTO> findAllProfessions();

    @PostMapping("/admin/professions")
    ProfessionDTO createProfession(@RequestBody ProfessionDTO dto);

    @DeleteMapping("/admin/professions/{id}")
    void deleteProfession(@PathVariable("id") Integer id);

    @GetMapping("/admin/professions/categories")
    List<CategorieDTO> findAllCategories();

    @PostMapping("/admin/professions/categories")
    CategorieDTO createCategorie(@RequestBody CategorieDTO dto);

    @DeleteMapping("/admin/professions/categories/{id}")
    void deleteCategorie(@PathVariable("id") Integer id);
}