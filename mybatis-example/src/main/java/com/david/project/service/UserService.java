package com.david.project.service;

import com.david.project.entity.User;
import com.david.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // CREATE
    public void createUser(User user) {
        userMapper.insert(user);
    }

    // SELECT
    public User getUserById(Integer id) {
        return userMapper.findById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    // UPDATE
    public void updateUser(User user) {
        userMapper.update(user);
    }

    // DELETE
    public void deleteUserById(Integer id) {
        userMapper.deleteById(id);
    }

}
