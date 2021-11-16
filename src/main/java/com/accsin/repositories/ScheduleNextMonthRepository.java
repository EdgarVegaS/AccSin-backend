package com.accsin.repositories;

import java.util.List;

import com.accsin.entities.views_entities.ScheduleServiceRequestView;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ScheduleNextMonthRepository extends PagingAndSortingRepository<ScheduleServiceRequestView,Long> {    
    
    @Query(nativeQuery = true, value = "select * from schedule_next_month")
    List<ScheduleServiceRequestView> getAll();

    @Query(nativeQuery = true, value = "select * from schedule_next_month where user_id = :id")
    List<ScheduleServiceRequestView> getByUser(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where user_id = :id and DATE_FORMAT(service_date, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(),'%Y-%m-%d' )")
    List<ScheduleServiceRequestView> getDailyByUser(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where user_id = :id and DATE_FORMAT(service_date, '%Y-%m-%d') = :date")
    List<ScheduleServiceRequestView> getDateByUser(@Param("id") String id, @Param("date") String date);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where user_id = :id and DATE_FORMAT(service_date, '%Y-%m-%d') between :date_start and :date_finish")
    List<ScheduleServiceRequestView> getBetweenDateByUser(@Param("id") String id, @Param("date_start") String dateStart,@Param("date_finish") String datefinish);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where professional_id = :id")
    List<ScheduleServiceRequestView> getByProfessional(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where professional_id = :id and DATE_FORMAT(service_date, '%Y-%m-%d') = DATE_FORMAT(SYSDATE(),'%Y-%m-%d' )")
    List<ScheduleServiceRequestView> getDailyByProfessional(@Param("id") String id);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where professional_id = :id and DATE_FORMAT(service_date, '%Y-%m-%d') = :date")
    List<ScheduleServiceRequestView> getDateByProfessional(@Param("id") String id, @Param("date") String date);

    @Query(nativeQuery = true, value = "select * from schedule_next_month where professional_id = :id and DATE_FORMAT(service_date, '%Y-%m-%d') between :date_start and :date_finish")
    List<ScheduleServiceRequestView> getBetweenDateByProfessional(@Param("id") String id, @Param("date_start") String dateStart,@Param("date_finish") String datefinish);
}
