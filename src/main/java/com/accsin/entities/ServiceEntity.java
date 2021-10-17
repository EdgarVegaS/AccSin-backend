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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Double contractPrice;

    @CreatedDate
    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;

    @OneToMany(mappedBy = "service")
    private List<MonthlyPaymentEntity> monthlyPayment;
}