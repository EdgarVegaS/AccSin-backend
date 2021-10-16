package com.accsin.controllers;

import java.util.List;

import com.accsin.models.request.TypeActionDetailModel;
import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.services.ActionTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actionTypes")
public class ActionTypeController {
    
    @Autowired
    ActionTypeService actionService;
    
    @GetMapping("/getActions")
    public List<ActionTypeDto> getActions(){
    	return actionService.getAllActionTypes();
    }
    @PostMapping("/updateAction")
    public List<ActionTypeDto> updateAction(@RequestBody TypeActionDetailModel typeActionDetails){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
    	return actionService.getAllActionTypes();
    }
}
