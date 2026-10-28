package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // GET /api/contrats  → liste tous les contrats
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    // GET /api/contrats/{id}  → récupère un contrat par id
    @GetMapping("/{id}")
    public ResponseEntity<Contrat> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contratService.findById(id));
    }

    // POST /api/contrats  → crée un nouveau contrat
    @PostMapping
    public ResponseEntity<Contrat> create(@RequestBody Contrat contrat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(contrat));
    }

    // PUT /api/contrats/{id}  → met à jour un contrat existant
    @PutMapping("/{id}")
    public ResponseEntity<Contrat> update(@PathVariable Long id, @RequestBody Contrat contrat) {
        return ResponseEntity.ok(contratService.update(id, contrat));
    }

    // DELETE /api/contrats/{id}  → supprime un contrat
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
