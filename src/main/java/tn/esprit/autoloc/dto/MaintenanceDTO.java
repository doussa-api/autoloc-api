package tn.esprit.autoloc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceDTO {

    private Long idMaintenance;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long vehiculeId;

    private String vehiculeImmatriculation;

    @JsonProperty("vehicule")
    public void unpackNestedVehicule(Map<String, Object> vehicule) {
        if (vehicule != null && vehicule.get("idVehicule") != null) {
            this.vehiculeId = Long.valueOf(vehicule.get("idVehicule").toString());
        }
    }
}

