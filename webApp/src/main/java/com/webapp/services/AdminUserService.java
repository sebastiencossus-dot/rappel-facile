package com.webapp.services;

import com.webapp.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final MsAdminClient msAdminClient; // ✅ remplace msJpaClient

    public List<User> findAll() {
        return msAdminClient.findAllUsers();
    }

    public User findById(Integer id) {
        return msAdminClient.findUserById(id);
    }

    public User update(Integer id, User user) {
        return msAdminClient.updateUser(id, user);
    }

    public void delete(Integer id) {
        msAdminClient.deleteUser(id);
    }
}