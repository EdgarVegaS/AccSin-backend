package com.accsin.models.responses;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserResponse  implements Serializable{

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private RoleResponse role;
    private ServiceUserResponse service;
    private String createAt;
    private String birthDate;
    private String rut;
}
