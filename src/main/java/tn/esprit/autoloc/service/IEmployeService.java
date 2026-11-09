package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.EmployeeDTO;

import java.util.List;

public interface IEmployeService {
    EmployeeDTO create(EmployeeDTO employeeDTO);
    EmployeeDTO findById(Long id);
    List<EmployeeDTO> findAll();
    EmployeeDTO update(Long id, EmployeeDTO employeeDTO);
    void deleteById(Long id);
}
