package com.david.project.service;

import com.david.project.entity.User;
import com.david.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    // SELECT
    public Mono<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> getUserByIdRange(Integer start, Integer end) {
        return userRepository.findByIdBetween(start, end);
    }

    // UPDATE
    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setGender(user.getGender());
                    existingUser.setPhoneNumber(user.getPhoneNumber());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setAddress(user.getAddress());
                    return userRepository.save(existingUser);
                });
    }

    // DELETE
    public Mono<Void> deleteUserById(Integer id) {
        return userRepository.deleteById(id);
    }
}
