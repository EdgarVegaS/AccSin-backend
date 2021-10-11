package com.accsin.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "service")
public class ServiceEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String serviceId;

    @Column(nullable = false)
    private boolean enable;

    @CreatedDate
    private Date createAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "service")
    private List<MonthlyPaymentEntity> monthlyPayment;
}
