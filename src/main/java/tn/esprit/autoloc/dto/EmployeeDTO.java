package tn.esprit.autoloc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import tn.esprit.autoloc.domain.RoleEmploye;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long idEmployee;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @NotBlank(message = "Le prénom de l'employé est obligatoire")
    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String prenom;

    @NotNull(message = "Le rôle de l'employé est obligatoire")
    private RoleEmploye role;

    @NotNull(message = "L'identifiant de l'agence est obligatoire")
    private Long agenceId;

    private String agenceNom;

    @JsonProperty("agence")
    public void unpackNestedAgence(Map<String, Object> agence) {
        if (agence != null && agence.get("idAgence") != null) {
            this.agenceId = Long.valueOf(agence.get("idAgence").toString());
        }
    }
}

