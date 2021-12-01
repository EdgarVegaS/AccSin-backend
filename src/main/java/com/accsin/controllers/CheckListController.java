package com.accsin.controllers;

import static com.accsin.utils.MethodsUtils.setCheckListResponse;

import java.util.List;

import com.accsin.models.request.CheckListRequest;
import com.accsin.models.request.MejorasListRequest;
import com.accsin.models.responses.CheckListResponse;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.OutMessage.MessageTipe;
import com.accsin.models.shared.dto.CheckListDto;
import com.accsin.models.shared.dto.ContractDto;
import com.accsin.models.shared.dto.CreateCheckListDto;
import com.accsin.services.interfaces.CheckListServiceInterface;
import com.accsin.services.interfaces.ContractServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checklists")
public class CheckListController {

    @Autowired
    CheckListServiceInterface checkListService;
    
    @Autowired
    ContractServiceInterface contractService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    ObjectMapper objMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @PostMapping
    public CheckListResponse createCheckList(@RequestBody CheckListRequest resquest){

        String mejoras = resquest.getJsonMejoras().toString();
        String check = resquest.getJsonList().toString();
        CreateCheckListDto createDto = CreateCheckListDto.builder().contractId(resquest.getContractId())
                                                                   .jsonList(check).jsonMejoras(mejoras)
                                                                   .build();
        CheckListDto dtoCreated = checkListService.createCheckList(createDto);
        
        return setCheckListResponse(dtoCreated, objMapper, logger, mapper);
    }
    
    @PostMapping("/updateCheckList")
    public CheckListResponse updateCheckList(@RequestBody MejorasListRequest request){
    	System.out.println(request.getUserId());
    	System.out.println(request.getJsonMejoras());
        String mejoras = request.getJsonMejoras().toString();
        List<ContractDto> listDto = contractService.getContractByUserId(request.getUserId());
        ContractDto contract = listDto.get(0);
        CreateCheckListDto createDto = CreateCheckListDto.builder().contractId(contract.getContractId())
                                                                   .jsonList(contract.getCheckList().getJsonList()).jsonMejoras(mejoras)
                                                                   .build();
        CheckListDto dtoCreated = checkListService.createCheckList(createDto);
        
        return setCheckListResponse(dtoCreated, objMapper, logger, mapper);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Object> getCheckListByUser(@PathVariable String id){
        OutMessage response = new OutMessage();
        try {

            CheckListDto dto = checkListService.getCheckListByUser(id);
            response.setMessageTipe(MessageTipe.OK);
            return ResponseEntity.ok().body(dto);

        } catch (Exception e) {

            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("ERROR al obtener check list de usuario");
			response.setDetail(e.getMessage());

        }

        return ResponseEntity.ok().body(response);
    }
}
