package com.accsin.repositories;

import java.util.Date;
import java.util.List;

import com.accsin.entities.recoveryPasswordEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface recoveryPasswordRepository extends PagingAndSortingRepository<recoveryPasswordEntity, Long> {
	
	@Query(value="SELECT id, create_at, email, enable, expired_at, temporal_code\r\n"
			+ "FROM recovery_password WHERE email = :email AND enable = true ORDER BY expired_at DESC LIMIT 1", nativeQuery=true)
	recoveryPasswordEntity findByEmail(String email);
	
	@Query(value="SELECT * FROM recovery_password WHERE email = :email AND temporal_code = :code AND enable = true AND expired_at > :dateNow ORDER BY expired_at DESC LIMIT 1", nativeQuery=true)
	recoveryPasswordEntity findByEmailAndCode(String email, String code, Date dateNow);
    
	@Query(value="SELECT * FROM recovery_password WHERE create_at BETWEEN :yesterday AND :today AND enable = true", nativeQuery=true)
	List<recoveryPasswordEntity> findAllenabled(Date today, Date yesterday);
    
}