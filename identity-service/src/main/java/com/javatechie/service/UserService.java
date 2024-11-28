package com.javatechie.service;

import com.javatechie.config.CustomUserDetailsService;
import com.javatechie.dto.UserDto;
import com.javatechie.entity.User;
import com.javatechie.mappers.UserMapper;
import com.javatechie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public void saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String generateToken(UserDetails userDetails) {
        return jwtUtil.generateToken(userDetails);

    }

    public ResponseEntity<String> validateToken(String token) {
        // Endpoint for validating the JWT token
            try {

                jwtUtil.validateToken(token);
                return ResponseEntity.ok("Token is valid");

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed: " + e.getMessage());
            }

    }
}
