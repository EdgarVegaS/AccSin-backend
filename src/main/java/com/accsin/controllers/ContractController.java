package com.accsin.controllers;

import java.util.List;

import com.accsin.models.request.CreateContractRequest;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.OutMessage.MessageTipe;
import com.accsin.models.shared.dto.ContractCreateDto;
import com.accsin.models.shared.dto.ContractDto;
import com.accsin.services.interfaces.ContractServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    
    @Autowired
    ContractServiceInterface contractService;

    @Autowired
    ModelMapper mapper;

    @PostMapping
    public ResponseEntity<Object> createContract(@RequestBody CreateContractRequest request){
        
        ContractCreateDto dto = mapper.map(request,ContractCreateDto.class);
        ContractDto respose = contractService.createContract(dto);

        return ResponseEntity.ok().body(respose);
    }

    @PutMapping
    public ResponseEntity<Object> updateContract(@RequestBody CreateContractRequest request){
        
        ContractCreateDto dto = mapper.map(request,ContractCreateDto.class);
        ContractDto respose = contractService.updateContract(dto,request.getContractId());

        return ResponseEntity.ok().body(respose);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteContract(@PathVariable String id){
        contractService.deleteContract(id);
        OutMessage msg = new OutMessage();
        msg.setMessageTipe(MessageTipe.OK);
        return ResponseEntity.ok().body(msg);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getContractsByUserId(@PathVariable String id){
        List<ContractDto> listDto = contractService.getContractByUserId(id);
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllContracts(){
        List<ContractDto> listDto = contractService.getAllContracts();
        return ResponseEntity.ok().body(listDto);
    }
}