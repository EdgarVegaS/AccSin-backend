package com.accsin.models.shared.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DateAvailableDto {
    
    private String year;
    private String month;
    private String day;
}
