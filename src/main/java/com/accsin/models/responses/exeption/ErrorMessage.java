package com.accsin.models.responses.exeption;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {
    
    private Date timestamp;
    private String message;
}
