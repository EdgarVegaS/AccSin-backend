package com.accsin.entities.views_entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Available_Days")
public class AvailableDaysView {
    
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "dates_count")
    private int datesCount;

    @Column(name = "dates")
    private String dates;

    @Column(name = "professional_total")
    private int professionalTotal;
}
