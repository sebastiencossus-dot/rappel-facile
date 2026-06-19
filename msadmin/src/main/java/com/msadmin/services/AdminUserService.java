package com.msadmin.services;

import com.msadmin.clients.MsJpaClient;
import com.msadmin.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final MsJpaClient msJpaClient;

    @Cacheable("adminUsers")
    public List<User> findAll() {
        return msJpaClient.findAllUsers();
    }

    public User findById(Integer id) {
        return msJpaClient.findUserById(id);
    }

    @CacheEvict(value = "adminUsers", allEntries = true)
    public User update(Integer id, User user) {
        return msJpaClient.updateUser(id, user);
    }

    @CacheEvict(value = "adminUsers", allEntries = true)
    public void delete(Integer id) {
        msJpaClient.deleteUser(id);
    }
}