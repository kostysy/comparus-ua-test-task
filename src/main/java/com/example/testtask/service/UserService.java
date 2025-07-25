package com.example.testtask.service;

import com.example.testtask.entity.User;
import com.example.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<UserRepository> userRepositories;

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (UserRepository userRepository : userRepositories) {
            allUsers.addAll(userRepository.findAll());
        }
        return allUsers;
    }
    @Autowired
    public void setUserRepositories(List<UserRepository> userRepositories) {
        this.userRepositories = userRepositories;
    }
}
