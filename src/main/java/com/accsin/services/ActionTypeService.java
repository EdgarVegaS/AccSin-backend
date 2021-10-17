package com.accsin.services;

import java.util.ArrayList;
import java.util.List;

import com.accsin.entities.ActionTypeEntity;
import com.accsin.repositories.ActionTypeRepository;
import com.accsin.services.interfaces.ActionTypeServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ActionTypeService implements ActionTypeServiceInterface {

    @Autowired
    ActionTypeRepository actionTypeRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public List<ActionTypeDto> getAllActionTypes() {
        Iterable<ActionTypeEntity> listEntity = actionTypeRepository.findAll();
        List<ActionTypeDto> listDto = new ArrayList<>();
        for (ActionTypeEntity actionTypeEntity : listEntity) {
            listDto.add(mapper.map(actionTypeEntity, ActionTypeDto.class));
        }
        return listDto;
    }
    @Override
    public ActionTypeDto updateActionType(ActionTypeDto actionType) throws Exception {
        ActionTypeEntity actionTypeEntity =  actionTypeRepository.findByActionTypeId(actionType.getActionTypeId());

        if (actionTypeEntity == null) {
            throw new Exception();
        }
        actionTypeEntity.setActionTypeName(actionType.getActionTypeName());
        actionTypeEntity.setContract_price(actionType.getContract_price());
        actionTypeEntity.setDuration(actionType.getDuration());
        actionTypeEntity.setEnable(actionType.getEnable());
        actionTypeEntity.setPrice(actionType.getPrice());
        actionTypeRepository.save(actionTypeEntity);
        return actionType;
    }
    
}
