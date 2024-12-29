package com.example.labs_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_id_laboratoire", nullable = false)
    @JsonBackReference
    private Laboratory laboratory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_adresse", nullable = false)
    private Adresse adresse;

    private String numTel;
    private String fax;

    @Column(length = 100)
    private String email;
}