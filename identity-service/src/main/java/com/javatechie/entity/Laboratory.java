package com.javatechie.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Laboratory {
    private Long id;
    private String nom;
    private byte[] logo; // Si nécessaire
    private String nrc;
    private Boolean active;
    private LocalDate dateActivation; // Si vous recevez une date sous forme de String
}
