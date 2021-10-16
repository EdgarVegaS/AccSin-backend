package com.accsin.controllers;

import java.util.List;

import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.models.shared.dto.MenuDto;
import com.accsin.services.ActionTypeService;
import com.accsin.services.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actionTypes")
public class ActionTypeController {
    
    @Autowired
    ActionTypeService actionService;
    
    @GetMapping("/getActions")
    public List<ActionTypeDto> getMenu(){
    	return actionService.getAllActionTypes();
    	
    }
}
