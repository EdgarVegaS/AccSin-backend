package com.accsin.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.accsin.models.request.CreateContractRequest;
import com.accsin.models.request.ImprovementHistoryRequest;
import com.accsin.models.request.UpdateContractRequest;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.OutMessage.MessageTipe;
import com.accsin.models.shared.dto.ContractCreateDto;
import com.accsin.models.shared.dto.ContractDto;
import com.accsin.models.shared.dto.ContractTypeDto;
import com.accsin.models.shared.dto.ImprovementHistoryDto;
import com.accsin.models.shared.dto.PaginationDto;
import com.accsin.services.interfaces.ContractServiceInterface;
import com.accsin.services.interfaces.ImprovementHistoryServiceInterface;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.expression.Ids;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    
    @Autowired
    ContractServiceInterface contractService;

	@Autowired
	ImprovementHistoryServiceInterface historyService;

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
			response.setDetail("El contrato ya est?? disponible en sistema para su uso");
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
    		contractService.updateContract(dto,request.getContractId());
    		response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Contrato Actualizado Exitosamente");
			response.setDetail("Se ha modificado con ??xito el contrato");
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

	@GetMapping("/getAll/pagination")
    public ResponseEntity<Object> getAllContracts(@RequestParam Optional<String> sortBy,
													@RequestParam Optional<Integer> page,
													@RequestParam Optional<Integer> quantity){
    	OutMessage response = new OutMessage();
    	try {
			PaginationDto dto = PaginationDto.builder().sortBy(sortBy).page(page).quantity(quantity).build();
    		List<ContractDto> listDto = contractService.getContractPagination(dto);
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

	@GetMapping("/improvement-history/all")
	public ResponseEntity<Object> getImprovementsHistory(){
		OutMessage response = new OutMessage();
		try {
			List<ImprovementHistoryDto> listDto = historyService.getAll();
			return ResponseEntity.ok().body(listDto);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Obteniendo el historial de mejoras");
			response.setDetail(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/improvement-history")
	public ResponseEntity<Object> getImprovementsHistoryAll(@RequestParam String userId){
		OutMessage response = new OutMessage();
		try {
			List<ImprovementHistoryDto> listDto = historyService.getByUser(userId);
			return ResponseEntity.ok().body(listDto);
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error Obteniendo el historial de mejoras");
			response.setDetail(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/improvement-history/create")
	public ResponseEntity<Object> createImprovementHistory(@RequestBody ImprovementHistoryRequest request){
		OutMessage response = new OutMessage();
		try {
			ImprovementHistoryDto ihDto = new ImprovementHistoryDto();
			ihDto.setDate(new Date());
			ihDto.setJsonImprovements(request.getJsonMejoras().toString());
			ihDto.setImprovementsNumber(request.getCountMejoras());
			ihDto.setUserId(request.getUserId());
			historyService.createImprovementHistory(ihDto);

			response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("historial de mejora Creado Exitosamente");

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se ha producido un error creando historial de mejoras");
			response.setDetail(e.getMessage());
		}

		return ResponseEntity.ok().body(response);
	}
}