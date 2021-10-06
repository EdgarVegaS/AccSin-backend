package com.accsin.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @CreatedDate
    private Date createAt;
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "check_list_id")
    private CheckListEntity checkList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @OneToMany(mappedBy = "user")
    private List<ActionEntity> actions;

}