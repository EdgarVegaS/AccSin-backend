package com.accsin.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "action_type")
public class ActionTypeEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String actionTypeId;

    @Column(nullable = false,length = 80)
    private String actionTypeName;

    @OneToMany(mappedBy = "actionType")
    private List<ActionEntity> actions;

}
