package com.accsin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "training_information")
public class TrainingInformationEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String trainingInformationId;

    @Column
    private String assistants;

    @Column
    private String materials;

    @Column
    private long serviceRequestId;
}
