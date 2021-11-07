package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.ScheduleEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends PagingAndSortingRepository<ScheduleEntity, Long> {
    
    @Query(value = "SELECT * FROM schedule s WHERE s.date BETWEEN CAST( :start AS DATE) AND CAST( :end AS DATE)", nativeQuery = true)
    List<ScheduleEntity> findByDateRange(@Param("start") String dateStart,@Param("end") String dateEnd);
    
    @Query(value = "SELECT * FROM schedule s WHERE s.professional_id = :user_id AND DATE_FORMAT(s.date, '%Y-%m-%d') = :date", nativeQuery = true)
    List<ScheduleEntity> findByUserAndDate(@Param("user_id") long userId,@Param("date") String date);
    
}
