package com.accsin.repositories;

import com.accsin.entities.TrainingInformationEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingInformationRepository extends CrudRepository<TrainingInformationEntity,Long> {
    
    TrainingInformationEntity findByTrainingInformationId(String trainingInformationId);
    TrainingInformationEntity findByServiceRequestId(long serviceRequestId);
}
