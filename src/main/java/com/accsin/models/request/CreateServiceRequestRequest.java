package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateServiceRequestRequest {

    private String clientEmail;
    private String dateSelected;
    private String serviceId;
    private String professionalId;
    
}
