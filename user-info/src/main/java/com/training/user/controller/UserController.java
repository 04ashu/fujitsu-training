package com.training.user.controller;

import com.training.user.entity.User;
import com.training.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> addUser(@RequestBody User user){
        log.info("Received request to add user with id: {}", user.getId());
        User addedUser = userService.addUser(user);
        log.info("User added successfully with id: {}", addedUser.getId());
        return ResponseEntity.ok(addedUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Fetching all users");

        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            log.info("No Users found");
            return ResponseEntity.noContent().build(); // 204
        }

        log.info("Total users fetched: {}", users.size());

        return ResponseEntity.ok(users); // 200
    }

    @GetMapping("/unique")
    public ResponseEntity<List<User>> getUniqueUsers() {
        log.info("Fetching unique users");
        List<User> users = userService.getUniqueUsers();
        log.info("Total unique users: {}", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.info("Fetching user with id: {}", id);
        User user = userService.getUserById(id);

        if (user == null) {
            log.warn("User not found with id: {}", id);
            return ResponseEntity.notFound().build(); // 404
        }

        log.info("User found with id: {}", id);
        return ResponseEntity.ok(user);
    }
}
