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
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

        Long laboratoryId = userDto.getFkIdLaboratoire();
        if (laboratoryId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Laboratory ID is null!");
        }

        Laboratory laboratory = laboratoryClient.getLaboratoryById(laboratoryId);
        if (laboratory == null || !laboratory.getActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Laboratory not found or not active!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSignature(signature);

        // Gestion des rôles
        Set<Role> roles = new HashSet<>();
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            for (String roleName : userDto.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseGet(() -> {
                            // Si le rôle n'existe pas, le créer et le sauvegarder
                            Role newRole = new Role(roleName);
                            return roleRepository.save(newRole);
                        });
                roles.add(role);
            }
        }
        user.setRoles(roles);

        userRepository.save(user);
    }


    public String generateToken(UserDetails userDetails) {
        return jwtUtil.generateToken(userDetails);
    }

    public ResponseEntity<String> validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed: " + e.getMessage());
        }
    }

    public void updateUser(int userId, UserUpdateRequest userRequest, byte[] signature) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));

        user.setFullName(userRequest.getFullName());
        user.setUsername(userRequest.getUsername());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        user.setProfession(userRequest.getProfession());

        Long laboratoryId = userRequest.getFkIdLaboratoire();
        if (laboratoryId != null) {
            Laboratory laboratory = laboratoryClient.getLaboratoryById(laboratoryId);
            if (laboratory == null || !laboratory.getActive()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Laboratory not found or not active!");
            }
            user.setFkIdLaboratoire(laboratoryId);
        }

        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : userRequest.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        if (signature != null) {
            user.setSignature(signature);
        }

        userRepository.save(user);
    }

    public List<UserUpdateRequest> getUsers() {

        List<UserUpdateRequest> userDtos= userRepository.findAll().stream().map(p -> userMapper.toEntity(p)).toList();

        userDtos.forEach(user -> {
            Long labId = user.getFkIdLaboratoire();
            if (labId != null) {
                try {
                    Laboratory laboratory = laboratoryClient.getLaboratoryById(labId);
                    if (laboratory != null) {
                        user.setLaboratory(laboratory);
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Laboratory not found or not active!");

                }
            }
        });

        return userDtos;
    }

    public void deleteUserById(int id) {
        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));

        // Supprimer l'utilisateur
        userRepository.delete(user);
    }
}
