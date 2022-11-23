package com.tingesoms.approvalservice.repositories;

import com.tingesoms.approvalservice.repositories.entities.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    List<ApprovalEntity> findByEmployeeRut(String employeeRut);
}
