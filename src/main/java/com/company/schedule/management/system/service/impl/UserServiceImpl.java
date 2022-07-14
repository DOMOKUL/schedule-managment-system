package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.User;
import com.company.schedule.management.system.repository.UserRepository;
import com.company.schedule.management.system.service.UserService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            return userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        LOGGER.debug("User with id = {} found: {}", id, user.get());
        return user.orElseThrow(() -> new ServiceException("User with id: " + id + " doesn't exist"));
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        LOGGER.debug("User with email = {} found: {}", email, user.get());
        return user.orElseThrow(() -> new ServiceException("User with email: " + email + " doesn't exist"));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        LOGGER.debug("Users found:{}", allUsers);
        return allUsers;
    }

    @Override
    public User updateUser(User user) {
        LOGGER.debug("User has been updated: {}", user);
        try {
            return userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            LOGGER.debug("User with id: {} has been deleted", id);
        } else {
            throw new ServiceException("User with id: " + id + " doesn't exist");
        }
    }
}
