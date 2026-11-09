package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EquipementDTO;
import tn.esprit.autoloc.service.IEquipementService;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    // GET /api/equipements
    @GetMapping
    public List<EquipementDTO> findAll() {
        return equipementService.findAll();
    }

    // GET /api/equipements/{id}
    @GetMapping("/{id}")
    public EquipementDTO findById(@PathVariable Long id) {
        return equipementService.findById(id);
    }

    // POST /api/equipements
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDTO create(@Valid @RequestBody EquipementDTO equipementDTO) {
        return equipementService.create(equipementDTO);
    }

    // PUT /api/equipements/{id}
    @PutMapping("/{id}")
    public EquipementDTO update(@PathVariable Long id, @Valid @RequestBody EquipementDTO equipementDTO) {
        return equipementService.update(id, equipementDTO);
    }

    // DELETE /api/equipements/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
