package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.ContractEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends PagingAndSortingRepository<ContractEntity, Long>{
    
    ContractEntity findByContractId(String contractId);
    List<ContractEntity> findByUserId(long userId);
}
