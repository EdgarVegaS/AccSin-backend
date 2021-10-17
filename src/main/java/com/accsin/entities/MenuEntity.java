package com.accsin.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "menu")
public class MenuEntity implements Serializable{
    
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String title; 

    @Column
    private String description;

    @Column
    private String route;
    
}
