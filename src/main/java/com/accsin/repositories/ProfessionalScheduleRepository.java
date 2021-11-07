package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.ProfessionalScheduleView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProfessionalScheduleRepository extends PagingAndSortingRepository<ProfessionalScheduleView,Long> {
    
    @Query(nativeQuery = true, value = "select * from proressional_schedule p where dates = :date")
    List<ProfessionalScheduleView> getByDate(@Param(value = "date") String date);
}
