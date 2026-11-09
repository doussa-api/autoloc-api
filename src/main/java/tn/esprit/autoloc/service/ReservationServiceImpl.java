package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public ReservationDTO create(ReservationDTO reservationDTO) {
        if (reservationDTO.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        if (reservationDTO.getDateFin() != null && reservationDTO.getDateDebut() != null &&
                reservationDTO.getDateFin().isBefore(reservationDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        Client client = clientRepository.findById(reservationDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", reservationDTO.getClientId()));

        Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", reservationDTO.getVehiculeId()));

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);

        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO findById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        return reservationMapper.toDto(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> findAll() {
        return reservationMapper.toDtoList(reservationRepository.findAll());
    }

    @Override
    public ReservationDTO update(Long id, ReservationDTO reservationDTO) {
        Reservation existing = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));

        if (reservationDTO.getDateFin() != null && reservationDTO.getDateDebut() != null &&
                reservationDTO.getDateFin().isBefore(reservationDTO.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }

        existing.setDateDebut(reservationDTO.getDateDebut());
        existing.setDateFin(reservationDTO.getDateFin());
        existing.setStatut(reservationDTO.getStatut());

        if (reservationDTO.getClientId() != null) {
            Client client = clientRepository.findById(reservationDTO.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", reservationDTO.getClientId()));
            existing.setClient(client);
        }

        if (reservationDTO.getVehiculeId() != null) {
            Vehicule vehicule = vehiculeRepository.findById(reservationDTO.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicule", reservationDTO.getVehiculeId()));
            existing.setVehicule(vehicule);
        }

        Reservation updated = reservationRepository.save(existing);
        return reservationMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }
}
