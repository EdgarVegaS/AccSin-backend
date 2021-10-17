package com.accsin.models.request;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckListRequest {
    
    private String contractId;
    private JsonNode jsonList;
    private JsonNode jsonMejoras;
}
