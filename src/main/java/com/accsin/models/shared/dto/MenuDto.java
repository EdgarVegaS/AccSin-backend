package com.accsin.models.shared.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto implements Serializable{
 
    private long id;
    private String title; 
    private String description;
    private String route;
    
}