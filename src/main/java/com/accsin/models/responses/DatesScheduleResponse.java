package com.accsin.models.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatesScheduleResponse {
    
    private String date;
    private List<String> hours = new ArrayList<>();
}
