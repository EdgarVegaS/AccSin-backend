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


    /*@Query(nativeQuery = true, value = "select c.id_professional, c.user_id, c.first_name, c.last_name, sum(c.conteo) as suma FROM ( select u.id as id_professional, u.user_id as user_id, u.first_name as first_name, u.last_name as last_name, COUNT(u.id) as conteo from `user` u left join schedule s on u.id = s.professional_id WHERE u.role_id = 4 and date_format(s.`date`, '%Y-%m-%d') = :date GROUP BY u.id UNION ALL SELECT u.id as id_professional, u.user_id as user_id, u.first_name as first_name, u.last_name as last_name, 0 as conteo from `user` u WHERE u.role_id = 4 GROUP BY u.id ) as c group by c.id_professional, c.user_id, c.first_name, c.last_name")
    List<ProfessionalAval> getProfessional(@Param("date") String date);*/
    
}