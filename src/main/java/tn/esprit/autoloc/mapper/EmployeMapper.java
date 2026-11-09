package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employee;
import tn.esprit.autoloc.dto.EmployeeDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeMapper {

    public EmployeeDTO toDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeDTO.builder()
                .idEmployee(employee.getIdEmployee())
                .nom(employee.getNom())
                .prenom(employee.getPrenom())
                .role(employee.getRole())
                .agenceId(employee.getAgence() != null ? employee.getAgence().getIdAgence() : null)
                .agenceNom(employee.getAgence() != null ? employee.getAgence().getNom() : null)
                .build();
    }

    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setIdEmployee(dto.getIdEmployee());
        employee.setNom(dto.getNom());
        employee.setPrenom(dto.getPrenom());
        employee.setRole(dto.getRole());
        return employee;
    }

    public List<EmployeeDTO> toDtoList(List<Employee> employees) {
        if (employees == null) {
            return Collections.emptyList();
        }
        return employees.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

