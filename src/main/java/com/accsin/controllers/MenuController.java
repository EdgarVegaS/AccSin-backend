package com.accsin.controllers;

import java.util.List;

import com.accsin.models.shared.dto.MenuDto;
import com.accsin.services.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;
    
    @GetMapping
    public List<MenuDto> getMenu(){

        return menuService.getAllMenu();
    }
}
