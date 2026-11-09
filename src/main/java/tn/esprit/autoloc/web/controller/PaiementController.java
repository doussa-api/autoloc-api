package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.PaiementDTO;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    // GET /api/paiements
    @GetMapping
    public List<PaiementDTO> findAll() {
        return paiementService.findAll();
    }

    // GET /api/paiements/{id}
    @GetMapping("/{id}")
    public PaiementDTO findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }

    // POST /api/paiements
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDTO create(@Valid @RequestBody PaiementDTO paiementDTO) {
        return paiementService.create(paiementDTO);
    }

    // PUT /api/paiements/{id}
    @PutMapping("/{id}")
    public PaiementDTO update(@PathVariable Long id, @Valid @RequestBody PaiementDTO paiementDTO) {
        return paiementService.update(id, paiementDTO);
    }

    // DELETE /api/paiements/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        paiementService.deleteById(id);
    }
}
