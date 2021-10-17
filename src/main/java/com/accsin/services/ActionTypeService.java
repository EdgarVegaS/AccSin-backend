package com.accsin.services;

import java.util.ArrayList;
import java.util.List;

import com.accsin.entities.ActionTypeEntity;
import com.accsin.models.shared.dto.ActionTypeDto;
import com.accsin.repositories.ActionTypeRepository;
import com.accsin.services.interfaces.ActionTypeServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionTypeService implements ActionTypeServiceInterface {

    @Autowired
    ActionTypeRepository actionTpeRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ActionTypeDto> getAllActionTypes() {
        Iterable<ActionTypeEntity> listEntity = actionTpeRepository.findAll();
        List<ActionTypeDto> listDto = new ArrayList<>();
        for (ActionTypeEntity actionTypeEntity : listEntity) {
            listDto.add(mapper.map(actionTypeEntity, ActionTypeDto.class));
        }
        return listDto;
    }
    
}
