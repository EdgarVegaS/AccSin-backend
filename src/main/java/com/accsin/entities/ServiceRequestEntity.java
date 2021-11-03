package com.accsin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "service_request")
public class ServiceRequestEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String servceRequestId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserEntity client;

    @OneToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private ScheduleEntity schudule;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

}