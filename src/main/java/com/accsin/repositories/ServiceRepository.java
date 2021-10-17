package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.ServiceEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<ServiceEntity,Long> {
    
    ServiceEntity findByServiceId(String serviceId);
    List<ServiceEntity> findByUserId(long userId);
}