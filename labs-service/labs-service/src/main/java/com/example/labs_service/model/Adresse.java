package com.example.labs_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numVoie;
    private String nomVoie;
    private String codePostal;
    private String ville;
    private String commune;

    @JsonIgnore
    @OneToMany(mappedBy = "adresse")
    private List<Contact> contacts;
}