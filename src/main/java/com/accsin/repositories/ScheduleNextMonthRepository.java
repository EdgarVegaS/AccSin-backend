package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.ScheduleNextMonthView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ScheduleNextMonthRepository extends PagingAndSortingRepository<ScheduleNextMonthView,Long> {    
    
    @Query(nativeQuery = true, value = "select * from schedule_next_month")
    List<ScheduleNextMonthView> getAll();

    @Query(nativeQuery = true, value = "select * from schedule_next_month where user_id = :id")
    List<ScheduleNextMonthView> getByUser(@Param("id") String id);
}
