package com.accsin.repositories;

import com.accsin.entities.MenuEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity,Long>  {
    
}
