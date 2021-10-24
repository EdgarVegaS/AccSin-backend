package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.UserEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

    @Query(value = "SELECT * FROM user u WHERE u.role_id = :role",nativeQuery = true)
    List<UserEntity> getUsersByRole(@Param("role") int role);
    
}