package com.accsin.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "improvement_history")
public class ImprovementHistoryEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String improvementHistoryId;

    @Column
    private String jsonImprovements;

    @Column
    private long userId;

    @Column
    private Date date;

    @Column
    private int improvementsNumber;
}
