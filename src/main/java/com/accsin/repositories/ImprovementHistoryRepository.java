package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.ImprovementHistoryEntity;

import org.springframework.data.repository.CrudRepository;

public interface ImprovementHistoryRepository extends CrudRepository<ImprovementHistoryEntity, Long> {

    List<ImprovementHistoryEntity> findByUserId(long userId);
}
