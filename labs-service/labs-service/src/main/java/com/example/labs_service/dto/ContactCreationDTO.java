package com.example.labs_service.dto;

import lombok.Data;

@Data
public class ContactCreationDTO {
    private Long laboratoryId;
    private String numTel;
    private String fax;
    private String email;
    private AdresseDTO adresse;
}