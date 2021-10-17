package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.models.shared.dto.UserDto;

public interface ActionTypeServiceInterface {
    
    public List<ActionTypeDto> getAllActionTypes();

	ActionTypeDto updateActionType(ActionTypeDto actionType) throws Exception;
}
