package com.accsin.services.interfaces;

import com.accsin.models.shared.dto.CheckListDto;
import com.accsin.models.shared.dto.CreateCheckListDto;

public interface CheckListServiceInterface {
    
    public CheckListDto createCheckList(CreateCheckListDto checklist);
    public CheckListDto getCheckListByUser(String userId);
	public CheckListDto updateCheckList(CreateCheckListDto checklist);

}
