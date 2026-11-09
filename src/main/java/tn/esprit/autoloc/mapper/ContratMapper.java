package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.dto.ContratDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContratMapper {

    public ContratDTO toDto(Contrat contrat) {
        if (contrat == null) {
            return null;
        }
        return ContratDTO.builder()
                .idContrat(contrat.getIdContrat())
                .dateSignature(contrat.getDateSignature())
                .montantTotal(contrat.getMontantTotal())
                .valide(contrat.isValide())
                .reservationId(contrat.getReservation() != null ? contrat.getReservation().getIdReservation() : null)
                .nombrePaiements(contrat.getPaiements() != null ? contrat.getPaiements().size() : 0)
                .build();
    }

    public Contrat toEntity(ContratDTO dto) {
        if (dto == null) {
            return null;
        }
        Contrat contrat = new Contrat();
        contrat.setIdContrat(dto.getIdContrat());
        contrat.setDateSignature(dto.getDateSignature());
        contrat.setMontantTotal(dto.getMontantTotal());
        contrat.setValide(dto.isValide());
        return contrat;
    }

    public List<ContratDTO> toDtoList(List<Contrat> contrats) {
        if (contrats == null) {
            return Collections.emptyList();
        }
        return contrats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

