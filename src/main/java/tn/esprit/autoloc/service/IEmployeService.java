package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Employee;

public interface IEmployeService {
    Employee create(Employee employee);
    Employee findById(Long id);
    List<Employee> findAll();
    Employee update(Long id, Employee employee);
    void deleteById(Long id);
}
