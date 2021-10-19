package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailRequestModel {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String create_at;
    private String birth_date;
    private String rut;

}
