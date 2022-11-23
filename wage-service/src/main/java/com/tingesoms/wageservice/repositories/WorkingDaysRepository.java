package com.tingesoms.wageservice.repositories;

import com.tingesoms.wageservice.entities.WorkingDaysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingDaysRepository extends JpaRepository<WorkingDaysEntity, Long> {
    WorkingDaysEntity findByMonth(int month);
}
