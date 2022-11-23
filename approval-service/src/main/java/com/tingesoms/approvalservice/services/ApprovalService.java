package com.tingesoms.approvalservice.services;

import com.tingesoms.approvalservice.models.EmployeeModel;
import com.tingesoms.approvalservice.repositories.ApprovalRepository;
import com.tingesoms.approvalservice.repositories.entities.ApprovalEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalService {
    private final ApprovalRepository approvalRepository;
    private final EmployeeService employeeService;

    public ApprovalService(ApprovalRepository approvalRepository, EmployeeService employeeService) {
        this.approvalRepository = approvalRepository;
        this.employeeService = employeeService;
    }

    public ApprovalEntity saveApproval(ApprovalEntity approval) {
        EmployeeModel employee;
        try {
            employee = this.employeeService.employeeByRut(approval.getEmployeeRut());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (employee == null) {
            return null;
        }
        return approvalRepository.save(approval);
    }

    public List<ApprovalEntity> getApprovals(){
        return approvalRepository.findAll();
    }

    public List<ApprovalEntity> getApprovalsByRut(String rut){
        return approvalRepository.findByEmployeeRut(rut);
    }

    public void deleteApproval(){
        approvalRepository.deleteAll();
    }

}
