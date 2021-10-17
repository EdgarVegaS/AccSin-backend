package com.accsin.controllers;

import java.util.List;

import com.accsin.models.request.TypeActionDetailModel;
import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.models.shared.dto.UserDto;
import com.accsin.services.ActionTypeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actionTypes")
public class ActionTypeController {
    
    @Autowired
    ActionTypeService actionService;
    
    @Autowired
    ModelMapper mapper;
    
    @GetMapping("/getActions")
    public List<ActionTypeDto> getActions(){
    	return actionService.getAllActionTypes();
    }
    @PutMapping("/updateAction")
    public List<ActionTypeDto> updateAction(@RequestBody TypeActionDetailModel typeActionDetails) throws Exception{
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        ActionTypeDto actionType = mapper.map(typeActionDetails, ActionTypeDto.class);
        try {
        	actionType = actionService.updateActionType(actionType);
		} catch (Exception e) {
			System.out.println("Se ha producido un error actualizando el ActionType");
		}
        
        System.out.println(typeActionDetails.getMail());
        //Edgar Hasta aca llegue
    	return actionService.getAllActionTypes();
    }
}
