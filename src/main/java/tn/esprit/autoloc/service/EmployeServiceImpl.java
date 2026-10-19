package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employee;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;

    @Override
    public Employee create(Employee employee) {
        if (employee.getIdEmployee() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }
        return employeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    @Override
    public List<Employee> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee existing = findById(id);
        existing.setNom(employee.getNom());
        existing.setPrenom(employee.getPrenom());
        existing.setRole(employee.getRole());
        existing.setAgence(employee.getAgence());
        return employeRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeRepository.deleteById(id);
    }
}
