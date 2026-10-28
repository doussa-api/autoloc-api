package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employee;
import tn.esprit.autoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    // GET /api/employes
    @GetMapping
    public List<Employee> findAll() {
        return employeService.findAll();
    }

    // GET /api/employes/{id}
    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    // POST /api/employes  – body must include agence by id
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee) {
        return employeService.create(employee);
    }

    // PUT /api/employes/{id}
    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) {
        return employeService.update(id, employee);
    }

    // DELETE /api/employes/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
