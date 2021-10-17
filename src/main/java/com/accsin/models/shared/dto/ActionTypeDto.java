package com.accsin.models.shared.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionTypeDto implements Serializable{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
    private String actionTypeName;
    private String actionTypeId;
    private Double contract_price;
    private Double price;
    private String created_at;
    private Double duration;
    private Boolean enable;
    
}