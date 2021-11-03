package com.accsin.models.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleProfesionalResponse {

    private String professionalId;
    private String professionalEmail;
    private String professionalName;
    private List<DatesScheduleResponse> dates = new ArrayList<>();
}
