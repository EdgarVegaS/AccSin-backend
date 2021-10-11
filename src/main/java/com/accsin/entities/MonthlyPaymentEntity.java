package com.accsin.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "monthly_payment")
public class MonthlyPaymentEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String monthlyPaymentId;

    @Column
    private Double total;

    @Column
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @OneToMany(mappedBy = "monthlyPayment")
    private List<PaymentEntity> payments;
}
