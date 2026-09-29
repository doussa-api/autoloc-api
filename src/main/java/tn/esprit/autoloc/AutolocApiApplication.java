package tn.esprit.autoloc;

import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class AutolocApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutolocApiApplication.class, args);
    }

    @Bean
    CommandLineRunner initDemoData(VehiculeRepository vehiculeRepository) {
        return args -> {
            if (vehiculeRepository.count() == 0) {

                // ✅ Constructeur avec 11 arguments :
                // idVehicule, immatriculation, marque, modele, categorie,
                // tarifJournalier, statut, agence, maintenances, reservations, equipements

                Vehicule v1 = new Vehicule(
                        null,                              // idVehicule
                        "TUN-1234",                        // immatriculation
                        "Peugeot",                         // marque
                        "208",                             // modele
                        CategorieVehicule.CITADINE,        // categorie
                        new BigDecimal("60.00"),           // tarifJournalier
                        StatutVehicule.DISPONIBLE,         // statut
                        null,                              // agence
                        null,                              // maintenances
                        null,                              // reservations
                        null                               // equipements
                );

                Vehicule v2 = new Vehicule(
                        null,
                        "TUN-5678",
                        "Volkswagen",
                        "Golf",
                        CategorieVehicule.BERLINE,
                        new BigDecimal("85.00"),
                        StatutVehicule.DISPONIBLE,
                        null,
                        null,
                        null,
                        null
                );

                Vehicule v3 = new Vehicule(
                        null,
                        "TUN-9012",
                        "Toyota",
                        "RAV4",
                        CategorieVehicule.SUV,
                        new BigDecimal("120.00"),
                        StatutVehicule.MAINTENANCE,
                        null,
                        null,
                        null,
                        null
                );

                vehiculeRepository.save(v1);
                vehiculeRepository.save(v2);
                vehiculeRepository.save(v3);

                System.out.println("✅ 3 véhicules de démonstration insérés.");
            } else {
                System.out.println("ℹ️ Véhicules déjà présents, insertion ignorée.");
            }
        };
    }
}