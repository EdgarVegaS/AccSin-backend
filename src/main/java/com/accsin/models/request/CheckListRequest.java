package com.accsin.models.request;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckListRequest {
    
    private String userEmail;
    private JsonNode jsonList;
    private JsonNode jsonMejoras;
}
