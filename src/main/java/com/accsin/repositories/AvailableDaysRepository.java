package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.AvailableDaysView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableDaysRepository extends PagingAndSortingRepository<AvailableDaysView,Long> {
    
    @Query(nativeQuery = true, value = "select * from Available_Days")
    List<AvailableDaysView> getAllRecords();
}
