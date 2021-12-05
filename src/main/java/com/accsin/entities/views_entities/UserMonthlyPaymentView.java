package com.accsin.entities.views_entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "user_monthly_payment_view")
public class UserMonthlyPaymentView {
    
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "monthly_payment_id")
    private String monthlyPaymentId;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "total")
    private double total;

    @Column(name = "payed")
    private boolean payed;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "user_id")
    private String userId;

}