package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratDTO create(ContratDTO contratDTO) {
        if (contratDTO.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        Reservation reservation = reservationRepository.findById(contratDTO.getReservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", contratDTO.getReservationId()));

        Contrat contrat = contratMapper.toEntity(contratDTO);
        contrat.setReservation(reservation);

        Contrat saved = contratRepository.save(contrat);
        return contratMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ContratDTO findById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        return contratMapper.toDto(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> findAll() {
        return contratMapper.toDtoList(contratRepository.findAll());
    }

    @Override
    public ContratDTO update(Long id, ContratDTO contratDTO) {
        Contrat existing = contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));

        existing.setDateSignature(contratDTO.getDateSignature());
        existing.setMontantTotal(contratDTO.getMontantTotal());
        existing.setValide(contratDTO.isValide());

        if (contratDTO.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(contratDTO.getReservationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reservation", contratDTO.getReservationId()));
            existing.setReservation(reservation);
        }

        Contrat updated = contratRepository.save(existing);
        return contratMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }
}
