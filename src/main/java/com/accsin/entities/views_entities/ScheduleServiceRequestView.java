package com.accsin.entities.views_entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "schedule_next_month")
public class ScheduleServiceRequestView {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "service_request_id")
    private String serviceRequestId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_rut")
    private String userRut;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "service_date")
    private Date serviceDate;
    
    @Column(name = "professional_id")
    private String profesionalId;

    @Column(name = "professional_rut")
    private String profesionalRut;

    @Column(name = "professional_name")
    private String profesionalName;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "name_service")
    private String nameService;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "observations")
    private String observations;

}
