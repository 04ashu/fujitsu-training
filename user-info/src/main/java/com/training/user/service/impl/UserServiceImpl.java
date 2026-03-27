package com.training.user.service.impl;

import com.training.user.entity.User;
import com.training.user.repository.UserRepository;
import com.training.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }

    public List<User> getUniqueUsers(){
        return userRepository.getUniqueUsers();
    }
}
