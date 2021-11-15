package com.accsin.models.shared.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractCreateDto {

    private String contractorCompany;
    private boolean requiredCheckList;
    private Double basePrice;
    private Double finalPrice;
    private boolean active;
    private String contractType;
    private List<ServiceCreateDto> services = new ArrayList<>();
    private CreateCheckListDto checkList;
}
