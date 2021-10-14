package com.accsin.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "menu")
public class MenuEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String title; 

    @Column
    private String description;

    @Column
    private String route;
    
    @ManyToMany(mappedBy = "menus")
    private List<RoleEntity> roles;
}
