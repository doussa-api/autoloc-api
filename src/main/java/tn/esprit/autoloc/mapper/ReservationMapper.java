package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ReservationDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {

    public ReservationDTO toDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        String clientNomComplet = null;
        if (reservation.getClient() != null) {
            clientNomComplet = reservation.getClient().getPrenom() + " " + reservation.getClient().getNom();
        }

        String vehiculeInfo = null;
        if (reservation.getVehicule() != null) {
            vehiculeInfo = reservation.getVehicule().getMarque() + " " +
                    reservation.getVehicule().getModele() + " (" +
                    reservation.getVehicule().getImmatriculation() + ")";
        }

        return ReservationDTO.builder()
                .idReservation(reservation.getIdReservation())
                .dateDebut(reservation.getDateDebut())
                .dateFin(reservation.getDateFin())
                .statut(reservation.getStatut())
                .clientId(reservation.getClient() != null ? reservation.getClient().getIdClient() : null)
                .clientNomComplet(clientNomComplet)
                .vehiculeId(reservation.getVehicule() != null ? reservation.getVehicule().getIdVehicule() : null)
                .vehiculeMarqueModele(vehiculeInfo)
                .contratId(reservation.getContrat() != null ? reservation.getContrat().getIdContrat() : null)
                .build();
    }

    public Reservation toEntity(ReservationDTO dto) {
        if (dto == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setIdReservation(dto.getIdReservation());
        reservation.setDateDebut(dto.getDateDebut());
        reservation.setDateFin(dto.getDateFin());
        reservation.setStatut(dto.getStatut());
        return reservation;
    }

    public List<ReservationDTO> toDtoList(List<Reservation> reservations) {
        if (reservations == null) {
            return Collections.emptyList();
        }
        return reservations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

