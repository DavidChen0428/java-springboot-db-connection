package com.david.project.service;

import com.david.project.entity.User;
import com.david.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    // SELECT
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    // UPDATE
    @Transactional
    public Optional<User> updateUser(User user) {
        return userRepository.findById(user.getId()).map(existingUser -> {
            existingUser.setName(user.getName());
            existingUser.setGender(user.getGender());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            return userRepository.save(existingUser);
        });
    }

    // DELETE
    public boolean deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
