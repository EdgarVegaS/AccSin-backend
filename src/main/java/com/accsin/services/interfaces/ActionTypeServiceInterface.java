package com.accsin.services.interfaces;

import java.util.List;

import com.accsin.models.shared.dto.ActionTypeDto;

public interface ActionTypeServiceInterface {
    
    public List<ActionTypeDto> getAllActionTypes();

	ActionTypeDto updateActionType(ActionTypeDto actionType) throws Exception;

	ActionTypeDto createActionType(ActionTypeDto actionType) throws Exception;
}
