package com.webapp.services;

import com.webapp.models.AdminRdvDTO;
import com.webapp.models.AdminUserDTO;
import com.webapp.models.User;
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
}