package com.accsin.models.shared.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionTypeDto implements Serializable{
 
    private long id;
    private String action_type_id; 
    private String action_type_name;
    private Double contract_price;
    private Double price;
    private String created_at;
    private Double duration;
    private Boolean enable;
    
}