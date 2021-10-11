package com.accsin.services;

import java.util.Date;
import java.util.UUID;

import com.accsin.entities.CheckListEntity;
import com.accsin.entities.UserEntity;
import com.accsin.models.shared.dto.CheckListDto;
import com.accsin.models.shared.dto.CreateCheckListDto;
import com.accsin.repositories.CheckListRepository;
import com.accsin.repositories.UserRepository;
import com.accsin.services.interfaces.CheckListServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListService implements CheckListServiceInterface {

    @Autowired
    ModelMapper mapper;

    @Autowired
    CheckListRepository checkListRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public CheckListDto createCheckList(CreateCheckListDto checklist) {
        
        UserEntity userEntity = userRepository.findByEmail(checklist.getUserEmail());
        CheckListEntity clEntity = new CheckListEntity();
        clEntity.setCheckListId(UUID.randomUUID().toString());
        clEntity.setUser(userEntity);
        clEntity.setJsonList(checklist.getJsonList());
        clEntity.setJsonMejoras(checklist.getJsonMejoras());
        clEntity.setCreateAt(new Date());
        CheckListEntity checkListCreated = checkListRepository.save(clEntity);
        return mapper.map(checkListCreated, CheckListDto.class);

    }
    
}
