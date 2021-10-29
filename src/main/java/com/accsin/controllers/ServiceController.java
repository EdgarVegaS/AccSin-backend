package com.accsin.controllers;

import java.util.ArrayList;
import java.util.List;

import com.accsin.models.request.ServiceCreateRequestModel;
import com.accsin.models.request.ServiceUpdateRequestModel;
import com.accsin.models.responses.OperationStatusModel;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.ServiceResponse;
import com.accsin.models.shared.dto.ServiceCreateDto;
import com.accsin.models.shared.dto.ServiceDto;
import com.accsin.services.interfaces.ServiceServiceInterface;
import com.accsin.services.interfaces.UserServiceInterface;

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
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    ModelMapper mapper;

    @Autowired
    ServiceServiceInterface serviceService;

    @Autowired
    UserServiceInterface userService;
    
    @GetMapping("/getServices")
    public ResponseEntity<Object> getActions(){
    	OutMessage response = new OutMessage();
    	try {
    		List<ServiceDto> servicesList = new ArrayList<ServiceDto>();
    		servicesList = serviceService.getAllService();
    		
            return
      			  ResponseEntity.ok().body(servicesList);

		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se produjo un error obteniendo la lista de servicios");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}


    }

    
    @PostMapping("/createService")
    public ResponseEntity<Object> createService(@RequestBody ServiceCreateRequestModel request){
    	OutMessage response = new OutMessage();
    	try {
            ServiceCreateDto serviceCreateDto = mapper.map(request, ServiceCreateDto.class);
            ServiceDto serviceDto = serviceService.createService(serviceCreateDto);
            response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Servicio Creado");
			response.setDetail("Se ha creado el servicio Exitosamente");
            return
        			  ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se produjo un error obteniendo la lista de servicios");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}

    }

    @PutMapping("/updateService")
    public ResponseEntity<Object> updateService(@RequestBody ServiceUpdateRequestModel requestModel){
    	OutMessage response = new OutMessage();
    	try {
            ServiceCreateDto serviceCreateDto = mapper.map(requestModel, ServiceCreateDto.class);
            serviceService.updateService(serviceCreateDto);
            response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Servicio Creado");
			response.setDetail("Se ha actualizado el servicio Exitosamente");
            return
            		ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se produjo un error actualizando el servicio");
			response.setDetail(e.getMessage());
			e.printStackTrace();
			return
					ResponseEntity.ok().body(response);
		}
 


    }

    @DeleteMapping("/{id}")
    public OperationStatusModel updateService(@PathVariable String id){
        serviceService.deleteService(id);
        return OperationStatusModel.builder().name("DELETE").result("success").build();
        
    }

    @GetMapping("/{id}")
    public ServiceDto getOneService(@PathVariable String id){
        return serviceService.getOneService(id);
    }

}
