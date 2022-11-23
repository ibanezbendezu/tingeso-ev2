package com.tingesoms.justificationservice.services;

import com.tingesoms.justificationservice.models.EmployeeModel;
import org.springframework.stereotype.Service;
import com.tingesoms.justificationservice.repositories.entities.JustificationEntity;
import com.tingesoms.justificationservice.repositories.JustificationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class JustificationService {
    private final JustificationRepository justificationRepository;
    private final EmployeeService employeeService;

    public JustificationService(JustificationRepository justificationRepository, EmployeeService employeeService) {
        this.justificationRepository = justificationRepository;
        this.employeeService = employeeService;
    }

    public JustificationEntity saveJustification(JustificationEntity justification) {
        EmployeeModel employee;
        try {
            employee = this.employeeService.employeeByRut(justification.getEmployeeRut());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (employee == null) {
            return null;
        }
        justification.setStatus(Boolean.FALSE);
        return justificationRepository.save(justification);
    }

    public List<JustificationEntity> getJustifications(){
        return justificationRepository.findAll();
    }

    public List<JustificationEntity> getJustificationsByRut(String rut){
        return justificationRepository.findByEmployeeRut(rut);
    }

    public void deleteJustification(){
        justificationRepository.deleteAll();
    }

}
