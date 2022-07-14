package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUserById(Long id);
}
