package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.dto.MaintenanceDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MaintenanceMapper {

    public MaintenanceDTO toDto(Maintenance maintenance) {
        if (maintenance == null) {
            return null;
        }
        return MaintenanceDTO.builder()
                .idMaintenance(maintenance.getIdMaintenance())
                .dateDebut(maintenance.getDateDebut())
                .dateFin(maintenance.getDateFin())
                .description(maintenance.getDescription())
                .vehiculeId(maintenance.getVehicule() != null ? maintenance.getVehicule().getIdVehicule() : null)
                .vehiculeImmatriculation(maintenance.getVehicule() != null ? maintenance.getVehicule().getImmatriculation() : null)
                .build();
    }

    public Maintenance toEntity(MaintenanceDTO dto) {
        if (dto == null) {
            return null;
        }
        Maintenance maintenance = new Maintenance();
        maintenance.setIdMaintenance(dto.getIdMaintenance());
        maintenance.setDateDebut(dto.getDateDebut());
        maintenance.setDateFin(dto.getDateFin());
        maintenance.setDescription(dto.getDescription());
        return maintenance;
    }

    public List<MaintenanceDTO> toDtoList(List<Maintenance> maintenances) {
        if (maintenances == null) {
            return Collections.emptyList();
        }
        return maintenances.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

