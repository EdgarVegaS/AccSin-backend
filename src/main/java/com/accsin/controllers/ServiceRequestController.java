package com.accsin.controllers;

import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.responses.OutMessage;
import com.accsin.services.interfaces.ServiceRequestServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/service-request")
public class ServiceRequestController {

    @Autowired
    ServiceRequestServiceInterface serviceRequestService;
    
    @PostMapping
    public ResponseEntity<Object> createServiceRequest(@RequestBody CreateServiceRequestRequest request){
        OutMessage response = new OutMessage();
        try {
            serviceRequestService.createServiceRequest(request);
            response.setMessageTipe(OutMessage.MessageTipe.OK);
			response.setMessage("Solicitud de servicio Creada");
			response.setDetail("Se ah creado la solicitud de forma exitosa");
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("ERROR al crear solicitud de servicio");
			response.setDetail(e.getMessage());
        }
        
        return ResponseEntity.ok().body(response);
    }
}
