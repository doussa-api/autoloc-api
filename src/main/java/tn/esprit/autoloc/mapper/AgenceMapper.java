package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgenceMapper {

    public AgenceDTO toDto(Agence agence) {
        if (agence == null) {
            return null;
        }
        return AgenceDTO.builder()
                .idAgence(agence.getIdAgence())
                .nom(agence.getNom())
                .ville(agence.getVille())
                .adresse(agence.getAdresse())
                .telephone(agence.getTelephone())
                .nombreEmployes(agence.getEmployees() != null ? agence.getEmployees().size() : 0)
                .nombreVehicules(agence.getVehicules() != null ? agence.getVehicules().size() : 0)
                .build();
    }

    public Agence toEntity(AgenceDTO dto) {
        if (dto == null) {
            return null;
        }
        Agence agence = new Agence();
        agence.setIdAgence(dto.getIdAgence());
        agence.setNom(dto.getNom());
        agence.setVille(dto.getVille());
        agence.setAdresse(dto.getAdresse());
        agence.setTelephone(dto.getTelephone());
        return agence;
    }

    public List<AgenceDTO> toDtoList(List<Agence> agences) {
        if (agences == null) {
            return Collections.emptyList();
        }
        return agences.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

