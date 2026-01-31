package com.afsar.task_manager.controller;

import com.afsar.task_manager.entity.User;
import com.afsar.task_manager.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        List<User> allUser = userService.getAllUser();

        if (!allUser.isEmpty())
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserById(@PathVariable String username) {
        User user = userService.findByUserName(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUserByUserName(@PathVariable String username) {
        userService.deleteByUserName(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable String username) {
        User user = userService.findByUserName(username);
        if (user != null) {
            user.setUserName(newUser.getUserName());
            user.setPassword(newUser.getPassword());
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
