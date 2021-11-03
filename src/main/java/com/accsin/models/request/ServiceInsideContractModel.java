package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceInsideContractModel {
	
	private String serviceId;
	private Integer quantity;
	private String name;
	private Double price;

}
