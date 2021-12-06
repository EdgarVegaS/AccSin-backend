package com.accsin.entities.views_entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "monthly_payment_view")
public class MonthlyPaymentView {
    
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "contract_id")
    private long contractId;

    @Column(name = "contract_price")
    private double contractPrice;

    @Column(name = "price_services")
    private double priceServices;

    @Column(name = "final_price")
    private double finalPrice;

    @Column(name = "date_service")
    private String dateService;
    
}