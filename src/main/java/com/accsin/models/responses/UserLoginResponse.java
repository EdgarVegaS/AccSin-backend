package com.accsin.models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponse {
    
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private RoleResponse role;
    private String token;

}
