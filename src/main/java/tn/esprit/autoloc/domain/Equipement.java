package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    @Column(nullable = false, length = 100)
    private String libelle;

    // ===== ASSOCIATION =====
    // Côté inverse : c'est Vehicule qui possède la @JoinTable
    @ManyToMany(mappedBy = "equipements")
    private List<Vehicule> vehicules;
}