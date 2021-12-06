package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.MonthlyPaymentView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPaymentViewRepository extends PagingAndSortingRepository<MonthlyPaymentView, Long>{

    @Query(nativeQuery = true, value = "select * from monthly_payment_view where date_service = :date")
    List<MonthlyPaymentView> getByDate(@Param("date") String date);
    
}