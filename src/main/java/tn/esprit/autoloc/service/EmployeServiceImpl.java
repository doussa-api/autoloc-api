package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employee;
import tn.esprit.autoloc.dto.EmployeeDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        if (employeeDTO.getIdEmployee() != null) {
            throw new IllegalArgumentException("Un nouvel employé ne doit pas avoir d'identifiant");
        }
        Agence agence = agenceRepository.findById(employeeDTO.getAgenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", employeeDTO.getAgenceId()));

        Employee employee = employeMapper.toEntity(employeeDTO);
        employee.setAgence(agence);
        Employee saved = employeRepository.save(employee);
        return employeMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDTO findById(Long id) {
        Employee employee = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        return employeMapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {
        return employeMapper.toDtoList(employeRepository.findAll());
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        existing.setNom(employeeDTO.getNom());
        existing.setPrenom(employeeDTO.getPrenom());
        existing.setRole(employeeDTO.getRole());

        if (employeeDTO.getAgenceId() != null) {
            Agence agence = agenceRepository.findById(employeeDTO.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", employeeDTO.getAgenceId()));
            existing.setAgence(agence);
        }

        Employee updated = employeRepository.save(existing);
        return employeMapper.toDto(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", id);
        }
        employeRepository.deleteById(id);
    }
}
