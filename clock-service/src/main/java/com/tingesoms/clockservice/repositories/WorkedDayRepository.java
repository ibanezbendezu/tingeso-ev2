package com.tingesoms.clockservice.repositories;

import com.tingesoms.clockservice.repositories.entities.WorkedDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkedDayRepository extends JpaRepository<WorkedDayEntity, Long> {
    List<WorkedDayEntity> findByEmployeeRut(String employeeRut);
}
