package com.example.labs_service.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class LaboratoryRequest {

    private Long id;
    private String nom;
    private String nrc;
    private Boolean active;
    private LocalDate dateActivation;

}
