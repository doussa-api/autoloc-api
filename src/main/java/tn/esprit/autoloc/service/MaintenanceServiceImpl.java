package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.MaintenanceDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceDTO create(MaintenanceDTO maintenanceDTO) {
        if (maintenanceDTO.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        if (maintenanceDTO.getDateFin() != null && maintenanceDTO.getDateFin().isBefore(maintenanceDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        Vehicule vehicule = vehiculeRepository.findById(maintenanceDTO.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", maintenanceDTO.getVehiculeId()));

        Maintenance maintenance = maintenanceMapper.toEntity(maintenanceDTO);
        maintenance.setVehicule(vehicule);

        Maintenance saved = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO findById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        return maintenanceMapper.toDto(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findAll() {
        return maintenanceMapper.toDtoList(maintenanceRepository.findAll());
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO maintenanceDTO) {
        Maintenance existing = maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));

        if (maintenanceDTO.getDateFin() != null && maintenanceDTO.getDateFin().isBefore(maintenanceDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        existing.setDateDebut(maintenanceDTO.getDateDebut());
        existing.setDateFin(maintenanceDTO.getDateFin());
        existing.setDescription(maintenanceDTO.getDescription());

        if (maintenanceDTO.getVehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(maintenanceDTO.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", maintenanceDTO.getVehiculeId()));
            existing.setVehicule(vehicule);
        }

        Maintenance updated = maintenanceRepository.save(existing);
        return maintenanceMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }
}
