package com.accsin.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "check_list")
public class CheckListEntity {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String checkListId;

    @Column(length = Integer.MAX_VALUE)
    private String jsonList;

    @Column(length = Integer.MAX_VALUE)
    private String jsonMejoras;

    @CreatedDate
    private Date createAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;

}