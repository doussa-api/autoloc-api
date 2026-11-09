package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.AgenceDTO;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    // GET /api/agences
    @GetMapping
    public List<AgenceDTO> findAll() {
        return agenceService.findAll();
    }

    // GET /api/agences/{id}
    @GetMapping("/{id}")
    public AgenceDTO findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    // POST /api/agences
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDTO create(@Valid @RequestBody AgenceDTO agenceDTO) {
        return agenceService.create(agenceDTO);
    }

    // PUT /api/agences/{id}
    @PutMapping("/{id}")
    public AgenceDTO update(@PathVariable Long id, @Valid @RequestBody AgenceDTO agenceDTO) {
        return agenceService.update(id, agenceDTO);
    }

    // DELETE /api/agences/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
