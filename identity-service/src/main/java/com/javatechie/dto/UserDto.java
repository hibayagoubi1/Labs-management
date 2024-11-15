package com.javatechie.dto;

import com.javatechie.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDto {

    private String fullName;
    private String username;
    private String password;
    private String profession;
    private Set<String> roles;

}
