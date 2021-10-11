package com.accsin.controllers;

import static com.accsin.utils.MethodsUtils.setCheckListResponse;

import com.accsin.models.request.CheckListRequest;
import com.accsin.models.responses.CheckListResponse;
import com.accsin.models.shared.dto.CheckListDto;
import com.accsin.models.shared.dto.CreateCheckListDto;
import com.accsin.services.interfaces.CheckListServiceInterface;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checklists")
public class CheckListController {

    @Autowired
    CheckListServiceInterface checkListService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ObjectMapper objMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @PostMapping
    public CheckListResponse createCheckList(@RequestBody CheckListRequest resquest){

        String mejoras = resquest.getJsonMejoras().toString();
        String check = resquest.getJsonList().toString();
        CreateCheckListDto createDto = CreateCheckListDto.builder().userEmail(resquest.getUserEmail())
                                                                   .jsonList(check).jsonMejoras(mejoras)
                                                                   .build();
        CheckListDto dtoCreated = checkListService.createCheckList(createDto);
        
        return setCheckListResponse(dtoCreated, objMapper, logger, mapper);
    }
}
