package com.accsin.repositories;

import com.accsin.entities.CheckListEntity;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListRepository extends CrudRepository<CheckListEntity,Long>{
	
    @Query(value = "SELECT * FROM check_list c WHERE c.contract_id = :id",nativeQuery = true)
    CheckListEntity getCheckListbyContractId(@Param("id") int role);
    
}