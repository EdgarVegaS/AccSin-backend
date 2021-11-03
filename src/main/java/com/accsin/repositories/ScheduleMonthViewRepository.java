package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.ScheduleMonthView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleMonthViewRepository extends CrudRepository<ScheduleMonthView,Long>{

    @Query(value = "SELECT * FROM schedule_month", nativeQuery = true)
    List<ScheduleMonthView> getAllRecord();
    
}
