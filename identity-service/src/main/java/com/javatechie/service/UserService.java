package com.javatechie.service;

import com.javatechie.config.CustomUserDetailsService;
import com.javatechie.dto.UserUpdateRequest;
import com.javatechie.entity.Laboratory;
import com.javatechie.entity.Role;
import com.javatechie.entity.User;
import com.javatechie.feignClient.LaboratoryClient;
import com.javatechie.mappers.UserMapper;
import com.javatechie.repository.RoleRepository;
import com.javatechie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {


    private final LaboratoryClient laboratoryClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    public UserService(LaboratoryClient laboratoryClient) {
        this.laboratoryClient = laboratoryClient;
    }


    public void saveUser(UserUpdateRequest userDto, byte[] signature) {
        User user = userMapper.toEntity(userDto);

        // Vérifiez que l'ID de laboratoire est valide
        Long laboratoryId = userDto.getFkIdLaboratoire();
        if (laboratoryId == null) {
            throw new IllegalArgumentException("Laboratory ID is null!");
        }

        // Vérifiez si le laboratoire existe et s'il est actif
        Laboratory laboratory = laboratoryClient.getLaboratoryById(laboratoryId);
        if (laboratory == null || !laboratory.getActive()) {
            throw new IllegalArgumentException("Laboratory not found or not active!");
        }

        // Chiffrez le mot de passe et enregistrez l'utilisateur
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSignature(signature);
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
    


    public void updateUser(int userId, UserUpdateRequest userRequest, byte[] signature) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Mettre à jour les champs
        user.setFullName(userRequest.getFullName());
        user.setUsername(userRequest.getUsername());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        user.setProfession(userRequest.getProfession());
        user.setFkIdLaboratoire(userRequest.getFkIdLaboratoire());

        // Gestion des rôles
        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : userRequest.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        // Mettre à jour la signature si elle est fournie
        if (signature != null) {
            user.setSignature(signature);
        }

        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
