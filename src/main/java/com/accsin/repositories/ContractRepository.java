package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.ContractEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends PagingAndSortingRepository<ContractEntity, Long>{
    
    ContractEntity findByContractId(String contractId);
    @Query(nativeQuery = true, value = "select * from contract where contractor_company = :userId AND active = true limit 1")
    List<ContractEntity> findByUserId(long userId);
}
