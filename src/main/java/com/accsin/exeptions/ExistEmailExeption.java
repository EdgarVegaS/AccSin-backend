package com.accsin.exeptions;

public class ExistEmailExeption extends RuntimeException {
    

    public ExistEmailExeption(String msg){
        super(msg);
    }
}