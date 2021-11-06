package com.accsin.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "recoveryPassword")
public class recoveryPasswordEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String temporalCode;
    
    @Column
    private Date createAt;
    
    @CreatedDate
    private Date expiredAt;
    
    @Column
    private Boolean enable;

}