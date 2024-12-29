package com.example.labs_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "laboratory", fetch = FetchType.EAGER)
    private List<Contact> contacts;
}
