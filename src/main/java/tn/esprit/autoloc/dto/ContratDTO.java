package tn.esprit.autoloc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {

    private Long idContrat;

    @NotNull(message = "La date de signature est obligatoire")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    @PositiveOrZero(message = "Le montant total doit être supérieur ou égal à zéro")
    private BigDecimal montantTotal;

    private boolean valide;

    @NotNull(message = "L'identifiant de la réservation est obligatoire")
    private Long reservationId;

    private Integer nombrePaiements;

    @JsonProperty("reservation")
    public void unpackNestedReservation(Map<String, Object> reservation) {
        if (reservation != null && reservation.get("idReservation") != null) {
            this.reservationId = Long.valueOf(reservation.get("idReservation").toString());
        }
    }
}

