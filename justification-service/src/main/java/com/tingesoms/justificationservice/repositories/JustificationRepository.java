package com.tingesoms.justificationservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tingesoms.justificationservice.repositories.entities.JustificationEntity;

import java.util.List;

public interface JustificationRepository extends JpaRepository<JustificationEntity, Long> {
    List<JustificationEntity> findByEmployeeRut(String employeeRut);
}
