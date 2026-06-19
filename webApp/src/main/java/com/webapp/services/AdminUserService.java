package com.webapp.services;

import com.webapp.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final MsJpaClient msJpaClient;

    public List<User> findAll() {
        return msJpaClient.findAllUsers();
    }

    public User findById(Integer id) {
        return msJpaClient.findUserById(id);
    }

    public User update(Integer id, User user) {
        return msJpaClient.updateUser(id, user);
    }

    public void delete(Integer id) {
        msJpaClient.deleteUser(id);
    }
}