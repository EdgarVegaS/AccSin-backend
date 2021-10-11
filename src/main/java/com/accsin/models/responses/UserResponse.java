package com.accsin.models.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private RoleResponse role;
    private ServiceResponse service;
}
