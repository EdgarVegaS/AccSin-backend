package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.UserMonthlyPaymentView;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMonthlyPaymentViewRepository extends CrudRepository<UserMonthlyPaymentView, Long>  {
    
    List<UserMonthlyPaymentView> findByUserId(String userId);
}