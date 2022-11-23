package com.tingesoms.employeeservice.services;

import com.tingesoms.employeeservice.repositories.EmployeeRepository;
import com.tingesoms.employeeservice.repositories.entities.EmployeeEntity;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeEntity> getEmployees(){ return employeeRepository.findAll(); }

    public void saveEmployee(EmployeeEntity employee){ employeeRepository.save(employee);}

    public EmployeeEntity getByRut(String rut){ return employeeRepository.findByRut(rut);}

    public void deleteEmployees(){ employeeRepository.deleteAll();}
}
