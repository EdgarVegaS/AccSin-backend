package com.accsin.services.interfaces;

import com.accsin.models.shared.dto.CheckListDto;
import com.accsin.models.shared.dto.CreateCheckListDto;

public interface CheckListServiceInterface {
    
    public CheckListDto createCheckList(CreateCheckListDto checklist);
}
