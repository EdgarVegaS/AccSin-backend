package com.accsin.models.shared.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckListDto {

    private String checkListId;
    private String jsonList;
    private String jsonMejoras;
    private Date createAt;
    private UserDto user;
}
