package com.accsin.models.request;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImprovementHistoryRequest {
    
    private String userId;
    private int countMejoras;
    private JsonNode jsonMejoras;
    
}
