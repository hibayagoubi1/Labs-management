package com.javatechie.dto;

import com.javatechie.entity.Laboratory;
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
    private Laboratory laboratory;
    private byte[] signature;
    private Set<String> roles; // Les noms des rôles
}
