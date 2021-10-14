package com.accsin.services;

import java.util.ArrayList;
import java.util.List;

import com.accsin.entities.MenuEntity;
import com.accsin.models.shared.dto.MenuDto;
import com.accsin.repositories.MenuRepository;
import com.accsin.services.interfaces.MenuServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService implements MenuServiceInterface {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<MenuDto> getAllMenu() {
        Iterable<MenuEntity> listEntity = menuRepository.findAll();
        List<MenuDto> listDto = new ArrayList<>();
        for (MenuEntity menuEntity : listEntity) {
            listDto.add(mapper.map(menuEntity, MenuDto.class));
        }
        return listDto;
    }
    
}
