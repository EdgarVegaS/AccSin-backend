package com.accsin.models.responses;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckListResponse {

    private String checkListId;
    private JsonNode jsonList;
    private JsonNode jsonMejoras;
    private Date createAt;
    private UserResponse user;
}
