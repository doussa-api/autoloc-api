package tn.esprit.autoloc.repository;

import tn.esprit.autoloc.domain.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}