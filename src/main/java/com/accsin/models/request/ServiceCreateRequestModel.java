package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceCreateRequestModel {

    private boolean enable;
    private String name;
    private Integer duration;
    private Double unitPrice;
    private Double contractPrice;
    private Integer quantity;
}
