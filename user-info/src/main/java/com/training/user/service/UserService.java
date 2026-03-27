package com.training.user.service;

import com.training.user.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    public User addUser(User user);

    public List<User> getAllUsers();

    public List<User> getUniqueUsers();

    public User getUserById(String id);
}
