package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceUpdateRequestModel {

    private String serviceId;
    private boolean enable;
    private String name;
    private Integer duration;
    private Double unitPrice;
    private Double contractPrice;
    private String userEmail;

}
