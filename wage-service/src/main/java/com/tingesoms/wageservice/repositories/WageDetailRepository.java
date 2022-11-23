package com.tingesoms.wageservice.repositories;

import com.tingesoms.wageservice.entities.WageDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageDetailRepository extends JpaRepository<WageDetailEntity, Long> {
}
