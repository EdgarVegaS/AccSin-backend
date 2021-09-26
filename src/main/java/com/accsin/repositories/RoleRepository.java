package com.accsin.repositories;


import com.accsin.entities.RoleEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {

    //RoleEntity findByRoleId(int id);
    RoleEntity findByName(String name);
    
}
