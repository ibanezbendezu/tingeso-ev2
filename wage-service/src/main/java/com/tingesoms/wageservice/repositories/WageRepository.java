package com.tingesoms.wageservice.repositories;

import com.tingesoms.wageservice.entities.WageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageRepository extends JpaRepository<WageEntity, Long> {
}
