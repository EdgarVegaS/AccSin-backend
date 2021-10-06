package com.accsin.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

    @OneToOne(mappedBy = "service")
    private UserEntity user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "monthly_payment_id")
    private MonthlyPaymentEntity monthlyPayment;
}
