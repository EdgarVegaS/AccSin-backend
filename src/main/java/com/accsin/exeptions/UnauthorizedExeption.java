package com.accsin.exeptions;

public class UnauthorizedExeption extends RuntimeException{
    
    public UnauthorizedExeption(String msg){
        super(msg);
    }
}
