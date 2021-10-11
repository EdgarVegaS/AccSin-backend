package com.accsin.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "payment")
public class PaymentEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String paymentId;

    @Column
    private Date datePayment;

    @ManyToOne
    @JoinColumn(name = "monthly_payment_id")
    private MonthlyPaymentEntity monthlyPayment;
}
