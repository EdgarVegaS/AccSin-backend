package com.accsin.repositories;

import com.accsin.entities.ServiceEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<ServiceEntity,Long> {
    
    ServiceEntity findByServiceId(String serviceId);
    ServiceEntity findByName(String name);
    //List<ServiceEntity> findByUserId(long userId);
}