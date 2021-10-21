package com.accsin.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user")
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(name= "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;
    
    @Column
    private Date birth_date;
    
    @Column (nullable = false)
    String rut;
    
    @CreatedDate
    private Date create_at;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<ContractEntity> contracts;

    @OneToMany(mappedBy = "user")
    private List<ActionEntity> actions;
}