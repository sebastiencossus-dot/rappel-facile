package com.msadmin.services;

import com.msadmin.models.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msjpa")
public interface MsJpaClient {

    @GetMapping("/users/all")
    List<User> findAllUsers();

    @GetMapping("/users/{id}")
    User findUserById(@PathVariable("id") Integer id);

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable("id") Integer id, @RequestBody User user);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Integer id);

    @GetMapping("/admin/stats")
    Map<String, Long> getAdminStats();

    @GetMapping("/admin/stats/recent-rdv")
    List<AdminRdvDTO> getRecentRdv();

    @GetMapping("/admin/stats/recent-users")
    List<AdminUserDTO> getRecentUsers();

    @GetMapping("/rdvs/all")
    List<AdminRdvDTO> getAllRdv();

    @GetMapping("/rdvs/all/statut/{statut}")
    List<AdminRdvDTO> getRdvByStatut(@PathVariable("statut") Integer statut);

    @DeleteMapping("/rdvs/admin/{id}")
    void deleteRdvAdmin(@PathVariable("id") Integer id);

    @GetMapping("/professions/admin")
    List<ProfessionDTO> getAllProfessionsAdmin();

    @PostMapping("/professions/admin")
    ProfessionDTO createProfession(@RequestBody ProfessionDTO dto);

    @DeleteMapping("/professions/admin/{id}")
    void deleteProfession(@PathVariable("id") Integer id);

    @GetMapping("/categories/admin")
    List<CategorieDTO> getAllCategoriesAdmin();

    @PostMapping("/categories/admin")
    CategorieDTO createCategorie(@RequestBody CategorieDTO dto);

    @DeleteMapping("/categories/admin/{id}")
    void deleteCategorie(@PathVariable("id") Integer id);
}