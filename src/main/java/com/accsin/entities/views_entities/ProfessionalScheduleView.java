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
@Table(name = "proressional_schedule")
public class ProfessionalScheduleView {
    
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "professional_id")
    private String professionalId;

    @Column(name = "dates")
    private String dates;

    @Column(name = "conteo")
    private int conteo;

}
