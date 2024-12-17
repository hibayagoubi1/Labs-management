package com.example.labs_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @Getter
    @Setter
    private byte[] logo;

    @Column(nullable = false, unique = true, length = 50)
    private String nrc;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "date_activation")
    private LocalDate dateActivation;
}
