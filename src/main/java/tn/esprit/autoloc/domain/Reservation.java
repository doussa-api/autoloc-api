package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutReservation statut;

    // ===== ASSOCIATIONS =====

    // Une réservation concerne un seul client
    @ManyToOne
    Client client;

    // Une réservation concerne un seul véhicule
    @ManyToOne
    Vehicule vehicule;

    // Une réservation donne lieu à un seul contrat (côté inverse du OneToOne)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reservation")
    Contrat contrat;
}