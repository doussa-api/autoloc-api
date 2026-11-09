package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO create(VehiculeDTO vehiculeDTO) {
        if (vehiculeDTO.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        Vehicule vehicule = vehiculeMapper.toEntity(vehiculeDTO);

        if (vehiculeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(vehiculeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", vehiculeDTO.getAgenceId()));
            vehicule.setAgence(agence);
        }

        if (vehiculeDTO.getEquipementIds() != null && !vehiculeDTO.getEquipementIds().isEmpty()) {
            List<Equipement> equipements = equipementRepository.findAllById(vehiculeDTO.getEquipementIds());
            vehicule.setEquipements(equipements);
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        return vehiculeMapper.toDto(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {
        return vehiculeMapper.toDtoList(vehiculeRepository.findAll());
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeDTO vehiculeDTO) {
        Vehicule existing = vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));

        existing.setImmatriculation(vehiculeDTO.getImmatriculation());
        existing.setMarque(vehiculeDTO.getMarque());
        existing.setModele(vehiculeDTO.getModele());
        existing.setCategorie(vehiculeDTO.getCategorie());
        existing.setTarifJournalier(vehiculeDTO.getTarifJournalier());
        existing.setStatut(vehiculeDTO.getStatut());

        if (vehiculeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(vehiculeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", vehiculeDTO.getAgenceId()));
            existing.setAgence(agence);
        }

        if (vehiculeDTO.getEquipementIds() != null) {
            List<Equipement> equipements = equipementRepository.findAllById(vehiculeDTO.getEquipementIds());
            existing.setEquipements(equipements);
        }

        Vehicule updated = vehiculeRepository.save(existing);
        return vehiculeMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }
}
