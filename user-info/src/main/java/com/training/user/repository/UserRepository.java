package com.training.user.repository;

import com.training.user.entity.User;
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
            return null; // or throw exception (later)
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
                .findFirst().orElse(null);
    }

    public List<User> getUniqueUsers(){

        Set<String> seenNames = new HashSet<>();

        return userList.stream()
                .filter(user -> seenNames.add(user.getName()))
                .collect(Collectors.toList());
    }
}
