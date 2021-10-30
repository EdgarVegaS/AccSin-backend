package com.accsin.repositories;

import com.accsin.entities.BusinessUserEntity;

import org.springframework.data.repository.CrudRepository;

public interface BusinessUserRepository extends CrudRepository<BusinessUserEntity,Long> {
    
}
