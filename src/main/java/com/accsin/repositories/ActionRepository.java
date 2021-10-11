package com.accsin.repositories;

import com.accsin.entities.ActionEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends CrudRepository<ActionEntity,Long> {
    
}
