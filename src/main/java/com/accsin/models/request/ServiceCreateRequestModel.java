package com.accsin.models.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceCreateRequestModel {

    private String userEmail;
    private String dayOfExpiration;

}
