package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.EmployeeDTO;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    // GET /api/employes
    @GetMapping
    public List<EmployeeDTO> findAll() {
        return employeService.findAll();
    }

    // GET /api/employes/{id}
    @GetMapping("/{id}")
    public EmployeeDTO findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    // POST /api/employes
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDTO create(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeService.create(employeeDTO);
    }

    // PUT /api/employes/{id}
    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeService.update(id, employeeDTO);
    }

    // DELETE /api/employes/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
