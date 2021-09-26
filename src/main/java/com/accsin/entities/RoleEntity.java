package com.accsin.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "role")
public class RoleEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String roleId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<UserEntity> users;

}