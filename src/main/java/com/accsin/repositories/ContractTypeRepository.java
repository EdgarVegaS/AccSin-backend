package com.accsin.repositories;

import com.accsin.entities.ContractTypeEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTypeRepository extends CrudRepository<ContractTypeEntity,Long>{
    
    ContractTypeEntity findByName(String name);
    
}
