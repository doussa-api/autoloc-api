package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;

import java.util.List;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    // GET /api/paiements
    @GetMapping
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }

    // GET /api/paiements/{id}
    @GetMapping("/{id}")
    public Paiement findById(@PathVariable Long id) {
        return paiementService.findById(id);
    }

    // POST /api/paiements  – body must include {"contrat":{"idContrat":1}, ...}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paiement create(@RequestBody Paiement paiement) {
        return paiementService.create(paiement);
    }

    // PUT /api/paiements/{id}
    @PutMapping("/{id}")
    public Paiement update(@PathVariable Long id, @RequestBody Paiement paiement) {
        return paiementService.update(id, paiement);
    }

    // DELETE /api/paiements/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        paiementService.deleteById(id);
    }
}
