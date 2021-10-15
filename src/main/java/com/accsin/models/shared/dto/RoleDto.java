package com.accsin.models.shared.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private String roleId;
    private String name;
    private List<MenuDto> menus;
    
}