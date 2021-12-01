package com.accsin.models.request;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MejorasListRequest {
    
    private String userId;
    private JsonNode jsonMejoras;
}
