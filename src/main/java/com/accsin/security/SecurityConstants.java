package com.accsin.security;

import com.accsin.SpringAppContext;

public class SecurityConstants {
    
    public static final long EXPIRATION_DATE = 864000000; //10 DIAS
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/users";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringAppContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
