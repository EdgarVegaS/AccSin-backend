package com.accsin.models.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCreateDto {
    
    private String serviceId;
    private boolean enable;
    private String name;
    private Integer duration;
    private Double unitPrice;
    private Double contractPrice;
    private String userEmail;
}
