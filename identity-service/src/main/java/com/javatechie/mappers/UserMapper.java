package com.javatechie.mappers;

import com.javatechie.dto.UserDto;
import com.javatechie.entity.Role;
import com.javatechie.entity.User;
import com.javatechie.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    @Autowired
    private RoleRepository roleRepository;

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setProfession(userDto.getProfession());

        // Convertir chaque rôle (String) en un objet Role
        user.setRoles(userDto.getRoles().stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                    return role;
                }).collect(Collectors.toSet()));

        return user;
    }
}
