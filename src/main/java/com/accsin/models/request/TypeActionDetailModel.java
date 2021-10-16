package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeActionDetailModel {
    
    private String action_type_name;
    private Double duration;
    private Double price;
    private Double contract_price;
    private String mail;
    private Boolean enable;
    
}
