package com.accsin.models.shared.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractDto {

    private String contractId;
    private String contractorCompany;
    private boolean requiredCheckList;
    private Double basePrice;
    private Double finalPrice;
    private boolean active;
    private Date createAt;
    private String userEmail;
    private String contractType;
    private List<ServiceDto> services = new ArrayList<>();
    private CheckListDto checkList;
}
