package com.javatechie.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserUpdateRequest {
    private int id;
    private String fullName;
    private String username;
    private String password;
    private String profession;
    private Long fkIdLaboratoire;
    private Set<String> roles; // Les noms des rôles
}
