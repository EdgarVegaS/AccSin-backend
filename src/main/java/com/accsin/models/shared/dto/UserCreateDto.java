package com.accsin.models.shared.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    
    private String userName;
    private String lastName;
    private String userRut;
    private String userMail;
    private String birthDate;
    private String perfil;
    private String position;
    private String category;
    private String specialCondition;

}
