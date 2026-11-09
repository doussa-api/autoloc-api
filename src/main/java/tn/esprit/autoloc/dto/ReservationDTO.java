package tn.esprit.autoloc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tn.esprit.autoloc.domain.StatutReservation;

import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {

    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    @NotNull(message = "Le statut de réservation est obligatoire")
    private StatutReservation statut;

    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long clientId;

    private String clientNomComplet;

    @NotNull(message = "L'identifiant du véhicule est obligatoire")
    private Long vehiculeId;

    private String vehiculeMarqueModele;

    private Long contratId;

    @JsonProperty("client")
    public void unpackNestedClient(Map<String, Object> client) {
        if (client != null && client.get("idClient") != null) {
            this.clientId = Long.valueOf(client.get("idClient").toString());
        }
    }

    @JsonProperty("vehicule")
    public void unpackNestedVehicule(Map<String, Object> vehicule) {
        if (vehicule != null && vehicule.get("idVehicule") != null) {
            this.vehiculeId = Long.valueOf(vehicule.get("idVehicule").toString());
        }
    }
}

