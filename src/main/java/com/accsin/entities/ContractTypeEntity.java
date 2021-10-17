package com.accsin.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "contract_type")
public class ContractTypeEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String contractTypeId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "contractType")
    private List<ContractEntity> contracts = new ArrayList<>() ;
}