package org.example.chattrilogy.controller;

import org.example.chattrilogy.domain.User;
import org.example.chattrilogy.service.UserService;
import org.example.chattrilogy.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUserUsingPost(@RequestBody User postManUser){
        String hashPassword = passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(postManUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) throws IdInvalidException {
        userService.handleDeleteUser(id);
        return ResponseEntity.ok("Delete user successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable("id") int id) throws IdInvalidException{
        User user = userService.fetchUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws IdInvalidException{
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping("/online")
    public ResponseEntity<List<User>> getAllUserOnline() {
        return ResponseEntity.ok(userService.getAllUserOnline());
    }
}
