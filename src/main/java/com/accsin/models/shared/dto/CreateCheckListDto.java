package com.accsin.models.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCheckListDto {

    private String contractId;
    private String jsonList;
    private String jsonMejoras;
    
}
