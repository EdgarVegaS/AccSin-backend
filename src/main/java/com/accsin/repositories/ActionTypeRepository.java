package com.accsin.repositories;

import java.util.concurrent.atomic.LongAdder;

import com.accsin.entities.ActionTypeEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTypeRepository extends CrudRepository<ActionTypeEntity,LongAdder> {

	ActionTypeEntity findByActionTypeId(String actionTypeId);
	
	ActionTypeEntity findByActionTypeName(String actionTypeName);
    
}
