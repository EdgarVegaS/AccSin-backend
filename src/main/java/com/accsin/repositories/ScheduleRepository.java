package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.ScheduleEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends PagingAndSortingRepository<ScheduleEntity, Long> {
    
    @Query(value = "SELECT * FROM schedule s WHERE s.date BETWEEN CAST( :start AS DATE) AND CAST( :end AS DATE)", nativeQuery = true)
    List<ScheduleEntity> findByDateRange(@Param("start") String dateStart,@Param("end") String dateEnd);
}
