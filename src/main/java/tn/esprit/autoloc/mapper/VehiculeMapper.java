package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiculeMapper {

    public VehiculeDTO toDto(Vehicule vehicule) {
        if (vehicule == null) {
            return null;
        }
        return VehiculeDTO.builder()
                .idVehicule(vehicule.getIdVehicule())
                .immatriculation(vehicule.getImmatriculation())
                .marque(vehicule.getMarque())
                .modele(vehicule.getModele())
                .categorie(vehicule.getCategorie())
                .tarifJournalier(vehicule.getTarifJournalier())
                .statut(vehicule.getStatut())
                .agenceId(vehicule.getAgence() != null ? vehicule.getAgence().getIdAgence() : null)
                .agenceNom(vehicule.getAgence() != null ? vehicule.getAgence().getNom() : null)
                .equipementIds(vehicule.getEquipements() != null ?
                        vehicule.getEquipements().stream().map(Equipement::getIdEquipement).collect(Collectors.toList()) :
                        Collections.emptyList())
                .equipementLibelles(vehicule.getEquipements() != null ?
                        vehicule.getEquipements().stream().map(Equipement::getLibelle).collect(Collectors.toList()) :
                        Collections.emptyList())
                .build();
    }

    public Vehicule toEntity(VehiculeDTO dto) {
        if (dto == null) {
            return null;
        }
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(dto.getIdVehicule());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setCategorie(dto.getCategorie());
        vehicule.setTarifJournalier(dto.getTarifJournalier());
        vehicule.setStatut(dto.getStatut());
        return vehicule;
    }

    public List<VehiculeDTO> toDtoList(List<Vehicule> vehicules) {
        if (vehicules == null) {
            return Collections.emptyList();
        }
        return vehicules.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

