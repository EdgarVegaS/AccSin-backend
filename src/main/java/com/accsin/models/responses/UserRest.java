package com.accsin.models.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRest {
    
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private RoleRest role;
    private String token;

}
