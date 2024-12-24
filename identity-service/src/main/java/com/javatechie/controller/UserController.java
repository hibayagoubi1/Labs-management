package com.javatechie.controller;

import com.javatechie.dto.UserUpdateRequest;
import com.javatechie.entity.User;
import com.javatechie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
   private UserService userService;


    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> updateUser(
            @PathVariable int userId,
            @ModelAttribute UserUpdateRequest userRequest,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Convert MultipartFile to byte[] for the signature
            byte[] signature = file != null ? file.getBytes() : null;
            userService.updateUser(userId, userRequest, signature);
            return ResponseEntity.ok("User updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + e.getMessage());
        }
    }
    @GetMapping
    public List<User>getUsers(){return userService.getUsers();}

}
