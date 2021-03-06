package com.accsin.models.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContractRequest {

    private String contractId;
    private String contractorCompany;
    private boolean requiredCheckList;
    private Double basePrice;
    private Double finalPrice;
    private boolean active;
    private String userEmail;
    private String contractType;
    private CheckListRequest checkList;
}
