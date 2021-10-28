package com.accsin.entities;

import java.util.Date;

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
@Entity(name = "schedule")
public class ScheduleEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String scheduleId;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private UserEntity professional;

    @OneToOne(mappedBy = "schudule")
    private ServiceRequestEntity serviceRequest;

}