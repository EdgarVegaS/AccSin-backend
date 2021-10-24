package com.accsin.models.shared.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleDto role;
    private ServiceDto service;
    private String createAt;
    private String birthDate;
    private String rut;
    private String businessUser;
    private String particularCondition;
    private String position;


}