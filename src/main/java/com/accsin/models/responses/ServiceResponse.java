package com.accsin.models.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class ServiceResponse {

    private String serviceId;
    private boolean enable;
    private String name;
    private Integer duration;
    private Double unitPrice;
    private Double contractPrice;
    private String userEmail;
}
