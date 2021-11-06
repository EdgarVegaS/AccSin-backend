package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceRequestRequest {

    private String clientId;
    private String dateSelected;
    private String serviceId;
    
}
