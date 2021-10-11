package com.accsin.models.shared.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCheckListDto {

    private String userEmail;
    private String jsonList;
    private String jsonMejoras;
    
}
