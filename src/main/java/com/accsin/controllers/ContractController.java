package com.accsin.controllers;

import java.util.List;

import com.accsin.models.request.CreateContractRequest;
import com.accsin.models.request.UpdateContractRequest;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.OutMessage.MessageTipe;
import com.accsin.models.shared.dto.ContractCreateDto;
import com.accsin.models.shared.dto.ContractDto;
import com.accsin.models.shared.dto.ContractTypeDto;
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

    @PostMapping("/createContract")
    public ResponseEntity<Object> createContract(@RequestBody CreateContractRequest request){
    	OutMessage response = new OutMessage();
    	try {
        	ContractCreateDto dto = mapper.map(request,ContractCreateDto.class);
        	contractService.createContract(dto);
			response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Contrato Creado Exitosamente");
			response.setDetail("El contrato ya está disponible en sistema para su uso");
        	return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error creando el contrato");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
        
    }

    @PutMapping("/updateContract")
    public ResponseEntity<Object> updateContract(@RequestBody UpdateContractRequest request){
    	OutMessage response = new OutMessage();
    	try {
    		ContractCreateDto dto = mapper.map(request,ContractCreateDto.class);
    		if(!request.isFreeChange()) {
    			//Lógica para cargarle un cobro al cliente
    		}
    		contractService.updateContract(dto,request.getContractId());
    		response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Contrato Actualizado Exitosamente");
			response.setDetail("Se ha modificado con éxito el contrato");
        	return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error creando el contrato");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
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

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllContracts(){
    	OutMessage response = new OutMessage();
    	try {
    		List<ContractDto> listDto = contractService.getAllContracts();
			response.setMessageTipe(OutMessage.MessageTipe.OK);
    		return ResponseEntity.ok().body(listDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Obteniendo los Contratos");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
    }
    @GetMapping("/getContractTypes") ResponseEntity<Object> getContractTypes(){
    	List<ContractTypeDto> listDto = contractService.getContractTypes();
    	return ResponseEntity.ok().body(listDto);
    }
}