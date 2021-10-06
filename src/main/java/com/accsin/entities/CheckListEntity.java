package com.accsin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

    @OneToOne(mappedBy = "checkList")
    private UserEntity user;

}
