package com.training.user.controller;

import com.training.user.entity.User;
import com.training.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @GetMapping("/slow")
    public String slowEndpoint() throws InterruptedException {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 3; i++) {
            Thread.sleep(2000); // simulate 3 independent slow calls
        }

        long end = System.currentTimeMillis();
        return "Response after delay. Time taken: " + (end - start) + " ms";
    }

    @GetMapping("/multi-thread")
    public String multiThreadEndpoint() throws InterruptedException {

        long start = System.currentTimeMillis();

        Runnable task = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        long end = System.currentTimeMillis();

        return "Multi-thread execution time: " + (end - start) + " ms";
    }

    @GetMapping("/executor")
    public String executor() throws Exception {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = Arrays.asList(
                () -> { Thread.sleep(2000); return "A"; },
                () -> { Thread.sleep(2000); return "B"; },
                () -> { Thread.sleep(2000); return "C"; }
        );

        executor.invokeAll(tasks);
        executor.shutdown();

        long end = System.currentTimeMillis();

        return "Executor time: " + (end - start) + " ms";
    }
}
