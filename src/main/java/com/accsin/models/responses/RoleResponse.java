package com.accsin.models.responses;

import java.util.List;

import com.accsin.models.shared.dto.MenuDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponse {

    private String name;
    private List<MenuDto> menus;
}
