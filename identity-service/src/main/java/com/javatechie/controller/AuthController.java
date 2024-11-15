package com.javatechie.controller;

import com.javatechie.config.CustomUserDetailsService;
import com.javatechie.dto.AuthRequest;
import com.javatechie.dto.UserDto;
import com.javatechie.service.JwtUtil;
import com.javatechie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;  // Service pour gérer les utilisateurs

    @Autowired
    private JwtUtil jwtUtil;  // Utilitaire pour les tokens JWT

    // Endpoint pour enregistrer un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.saveUser(userDto);  // Enregistrer le nouvel utilisateur
            return ResponseEntity.ok("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed: " + e.getMessage());
        }
    }

    // Endpoint pour authentifier un utilisateur et générer un token JWT
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            return userService.generateToken(userDetails);
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        return userService.validateToken(token);
    }

}
