package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 50)
    private String ville;

    @Column(nullable = false, length = 150)
    private String adresse;

    @Column(nullable = false, length = 20)
    private String telephone;

    // ===== ASSOCIATIONS (style cours bidirectionnel) =====

    // Une agence a plusieurs employés
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
    private List<Employee> employees;

    // Une agence a plusieurs véhicules
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agence")
    private List<Vehicule> vehicules;
}