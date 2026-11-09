package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ContratDTO;
import tn.esprit.autoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // GET /api/contrats
    @GetMapping
    public List<ContratDTO> findAll() {
        return contratService.findAll();
    }

    // GET /api/contrats/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ContratDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contratService.findById(id));
    }

    // POST /api/contrats
    @PostMapping
    public ResponseEntity<ContratDTO> create(@Valid @RequestBody ContratDTO contratDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratService.create(contratDTO));
    }

    // PUT /api/contrats/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ContratDTO> update(@PathVariable Long id, @Valid @RequestBody ContratDTO contratDTO) {
        return ResponseEntity.ok(contratService.update(id, contratDTO));
    }

    // DELETE /api/contrats/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
