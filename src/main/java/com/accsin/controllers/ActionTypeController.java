package com.accsin.controllers;

import java.util.ArrayList;
import java.util.List;

import com.accsin.models.request.TypeActionDetailModel;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.services.ActionTypeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actionTypes")
public class ActionTypeController {
    
    @Autowired
    ActionTypeService actionService;
    
    @Autowired
    ModelMapper mapper;
    
    @GetMapping("/getActions")
    public ResponseEntity<Object> getActions(){
    	OutMessage response = new OutMessage();
    	try {
    		List<ActionTypeDto> actionsList = new ArrayList<ActionTypeDto>();
        	actionsList = actionService.getAllActionTypes();
            return
      			  ResponseEntity.ok().body(actionsList);
			
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se produjo un error obteniendo la lista de servicios");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
    	
    	
    }
    
    @PutMapping("/updateAction")
    public ResponseEntity<Object> updateAction(@RequestBody TypeActionDetailModel typeActionDetails) throws Exception{
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        OutMessage response = new OutMessage();
        ActionTypeDto actionType = mapper.map(typeActionDetails, ActionTypeDto.class);
        try {
        	actionType = actionService.updateActionType(actionType);
        	response.setMessageTipe(OutMessage.MessageTipe.OK);
		} catch (Exception e) {
			System.out.println("Se ha producido un error actualizando el ActionType");
		}
        return
  			  ResponseEntity.ok().body(response);
    }
    
    @PostMapping("/createAction")
    public ResponseEntity<Object> createAction(@RequestBody TypeActionDetailModel typeActionDetails) throws Exception{
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        OutMessage response = new OutMessage();
        ActionTypeDto actionType = mapper.map(typeActionDetails, ActionTypeDto.class);
        try {
        	actionType = actionService.createActionType(actionType);
        	response.setMessageTipe(OutMessage.MessageTipe.OK);
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error creando el ActionType");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
        return
  			  ResponseEntity.ok().body(response);
    }
    
}
