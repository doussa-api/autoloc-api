package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final IContratRepository contratRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementDTO create(PaiementDTO paiementDTO) {
        if (paiementDTO.getIdPaiement() != null) {
            throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant");
        }
        Contrat contrat = contratRepository.findById(paiementDTO.getContratId())
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", paiementDTO.getContratId()));

        Paiement paiement = paiementMapper.toEntity(paiementDTO);
        paiement.setContrat(contrat);

        Paiement saved = paiementRepository.save(paiement);
        return paiementMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO findById(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
        return paiementMapper.toDto(paiement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> findAll() {
        return paiementMapper.toDtoList(paiementRepository.findAll());
    }

    @Override
    public PaiementDTO update(Long id, PaiementDTO paiementDTO) {
        Paiement existing = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));

        existing.setMontant(paiementDTO.getMontant());
        existing.setDatePaiement(paiementDTO.getDatePaiement());
        existing.setModePaiement(paiementDTO.getModePaiement());

        if (paiementDTO.getContratId() != null) {
            Contrat contrat = contratRepository.findById(paiementDTO.getContratId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contrat", paiementDTO.getContratId()));
            existing.setContrat(contrat);
        }

        Paiement updated = paiementRepository.save(existing);
        return paiementMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!paiementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement", id);
        }
        paiementRepository.deleteById(id);
    }
}
