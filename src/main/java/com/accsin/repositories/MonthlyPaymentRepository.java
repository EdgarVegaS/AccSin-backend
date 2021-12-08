package com.accsin.repositories;

import com.accsin.entities.MonthlyPaymentEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPaymentRepository extends CrudRepository<MonthlyPaymentEntity,Long> {
    
    MonthlyPaymentEntity findByMonthlyPaymentId(String monthlyPaymentId);

    @Query(nativeQuery = true, value = "SELECT * FROM monthly_payment m WHERE m.month =:month AND m.contract_id =:contractId")
    MonthlyPaymentEntity getByContractAndMonth(@Param("contractId") long contractId,@Param("month") String month); 
}
