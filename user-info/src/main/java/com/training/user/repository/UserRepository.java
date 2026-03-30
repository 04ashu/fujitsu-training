package com.training.user.repository;

import com.training.user.entity.User;
import com.training.user.exception.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private List<User> userList = new ArrayList<>();

    public User addUser(User user){
        boolean exists = userList.stream()
                .anyMatch(u -> Objects.equals(u.getId(), user.getId()));

        if (exists) {
            throw new IllegalArgumentException("User with ID already exists: " + user.getId());
        }

        userList.add(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public User getUserById(String id) {
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst().orElseThrow(() ->
                        new UserNotFoundException("User not found with id: " + id));
    }

    public List<User> getUniqueUsers(){

        Set<String> seenNames = new HashSet<>();

        return userList.stream()
                .filter(user -> seenNames.add(user.getName()))
                .collect(Collectors.toList());
    }
}
