package com.accsin.models.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingInformationDto {

    private String trainingInformationId;
    private String assistants;
    private String materials;
    private long serviceRequestId;

}
