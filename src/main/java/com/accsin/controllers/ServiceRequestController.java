package com.accsin.controllers;

import com.accsin.models.request.CreateServiceRequestRequest;
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
        
        serviceRequestService.createServiceRequest(request);
        return null;
    }
}
