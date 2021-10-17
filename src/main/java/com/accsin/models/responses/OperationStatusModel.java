package com.accsin.models.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OperationStatusModel {

    private String name;
    private String result;

}
