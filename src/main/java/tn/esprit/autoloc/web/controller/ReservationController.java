package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ReservationDTO;
import tn.esprit.autoloc.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    // GET /api/reservations
    @GetMapping
    public List<ReservationDTO> findAll() {
        return reservationService.findAll();
    }

    // GET /api/reservations/{id}
    @GetMapping("/{id}")
    public ReservationDTO findById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    // POST /api/reservations
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO create(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.create(reservationDTO);
    }

    // PUT /api/reservations/{id}
    @PutMapping("/{id}")
    public ReservationDTO update(@PathVariable Long id, @Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationService.update(id, reservationDTO);
    }

    // DELETE /api/reservations/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
