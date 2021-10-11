package com.accsin.repositories;

import java.util.concurrent.atomic.LongAdder;

import com.accsin.entities.ActionTypeEntity;

import org.springframework.data.repository.CrudRepository;

public interface ActionTypeRepository extends CrudRepository<ActionTypeEntity,LongAdder> {
    
}
