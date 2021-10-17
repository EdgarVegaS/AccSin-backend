package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeActionDetailModel {
	
	private long id;
    private String actionTypeName;
    private Double duration;
    private Double price;
    private Double contract_price;
    private String mail;
    private Boolean enable;
    private String actionTypeId;
    
}
