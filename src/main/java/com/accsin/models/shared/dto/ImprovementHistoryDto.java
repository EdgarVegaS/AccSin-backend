package com.accsin.models.shared.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ImprovementHistoryDto {
    
    private String improvementHistoryId;
    private String jsonImprovements;
    private long userId;
    private Date date;
    private int improvementsNumber;
}
