package com.accsin.repositories;

import com.accsin.entities.CheckListEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends CrudRepository<CheckListEntity,Long>{
    
}