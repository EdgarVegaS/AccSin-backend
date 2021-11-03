package com.accsin.entities.views_entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "schedule_month")
public class ScheduleMonthView implements Serializable{

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "professional_id")
    private String professionalId;
    
    @Column(name = "schedule_day")
    private String scheduleDay;

    @Column(name = "professional_name")
    private String professionalName;

    @Column(name = "professional_email")
    private String professionalEmail;

    @Column(name = "nine")
    private String nineHour;

    @Column(name = "twelve")
    private String twelveHour;

    @Column(name = "fifteen")
    private String fifteenHour;
}
