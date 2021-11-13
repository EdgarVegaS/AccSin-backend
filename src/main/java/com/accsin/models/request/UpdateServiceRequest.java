package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateServiceRequest {
    
    private String serviceRequestId;
    private String dateSelected;
    private String serviceId;
}
