package com.accsin.entities;

import java.util.ArrayList;
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
@Entity(name = "contract")
public class ContractEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String contractId;

    @Column(nullable = false)
    private String contractorCompany;

    @Column
    private boolean requiredCheckList;

    @Column
    private Double basePrice;

    @Column
    private Double finalPrice;

    @Column
    private boolean active;

    @CreatedDate
    private Date createAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "contract_type_id")
    private ContractTypeEntity contractType;

    @OneToOne(mappedBy = "contract",cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private CheckListEntity checkList;

    @OneToMany(mappedBy = "contract")
    private List<MonthlyPaymentEntity> monthlyPayments;
}