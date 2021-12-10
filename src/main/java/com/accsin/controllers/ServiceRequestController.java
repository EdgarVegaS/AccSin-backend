package com.accsin.controllers;

import java.util.List;

import com.accsin.models.request.CreateServiceRequestRequest;
import com.accsin.models.request.UpdateServiceRequest;
import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.OutMessage.MessageTipe;
import com.accsin.models.shared.dto.ScheduleNextMonthDto;
import com.accsin.models.shared.dto.TrainingInformationDto;
import com.accsin.services.interfaces.ServiceRequestServiceInterface;

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

@RestController
@RequestMapping("/api/service-request")
public class ServiceRequestController {

    @Autowired
    ServiceRequestServiceInterface serviceRequestService;

    @PostMapping("/createServiceRequest")
    public ResponseEntity<Object> createServiceRequest(@RequestBody CreateServiceRequestRequest request) {
        OutMessage response = new OutMessage();
        try {
            serviceRequestService.createServiceRequest(request);
            response.setMessageTipe(OutMessage.MessageTipe.OK);
            response.setMessage("Solicitud de servicio Creada");
            response.setDetail("Se ha creado la solicitud de forma exitosa");
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("ERROR al crear solicitud de servicio");
            response.setDetail(e.getMessage());
        }

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> canceleService(@PathVariable String id) {
        OutMessage response = new OutMessage();
        try {
            serviceRequestService.deleteServiceRequest(id);
            response.setMessageTipe(MessageTipe.OK);
            response.setMessage("Eliminacion se solicitud Exitosa");
            response.setDetail("Se ha cancelado la solicitud de manera exitosa");

        } catch (Exception e) {
        	e.printStackTrace();
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("ERROR cancelando la solicitud de servicio");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/updateServiceRequest")
    public ResponseEntity<Object> updateServiceRequest(@RequestBody UpdateServiceRequest request) {
        OutMessage response = new OutMessage();
        try {
            serviceRequestService.updateServiceRequest(request);
            response.setMessageTipe(MessageTipe.OK);
            response.setMessage("Actualizacion de solicitud Exitosa");
            response.setDetail("Se ha actualizado la solicitud de manera exitosa");
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("ERROR al Actualizar solicitud de servicio");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/schedule-next-month")
    public ResponseEntity<Object> getServiceRequestNextMonth(@RequestParam String date) {
        OutMessage response = new OutMessage();
        try {
            List<ScheduleNextMonthDto> listDto = serviceRequestService.getNextMonthServices(date);
            return ResponseEntity.ok().body(listDto);
        } catch (Exception e) {
        	e.printStackTrace();
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener listado de servicios");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<Object> getServicesByUserId(@RequestParam String id,@RequestParam String type) {
        OutMessage response = new OutMessage();
        try {
            List<ScheduleNextMonthDto> listDto = serviceRequestService.getNextMonthServicesByUser(id,type);
            return ResponseEntity.ok().body(listDto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener listado de servicios");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/daily")
    public ResponseEntity<Object> getDailyServicesByUserId(@RequestParam String id,@RequestParam String type) {
        OutMessage response = new OutMessage();
        try {
            List<ScheduleNextMonthDto> listDto = serviceRequestService.getDailyServicesByUser(id,type);
            return ResponseEntity.ok().body(listDto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener listado de servicios");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/by-date")
    public ResponseEntity<Object> getDateServicesByUserId(@RequestParam String id, @RequestParam String date,@RequestParam String type) {
        OutMessage response = new OutMessage();
        try {
            List<ScheduleNextMonthDto> listDto = serviceRequestService.getDateServicesByUser(id,date,type);
            return ResponseEntity.ok().body(listDto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener listado de servicios");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/between-date")
    public ResponseEntity<Object> getBetweenDateServicesByUserId(@RequestParam String id, @RequestParam String dateStart,@RequestParam String dateFinish,@RequestParam String type) {
        OutMessage response = new OutMessage();
        try {
            List<ScheduleNextMonthDto> listDto = serviceRequestService.getBetweenDateServicesByUser(id,dateStart,dateFinish,type);
            return ResponseEntity.ok().body(listDto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener listado de servicios");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/service-request-id")
    public ResponseEntity<Object> getBetweenDateServicesByUserId(@RequestParam String id) {
        OutMessage response = new OutMessage();
        try {
            ScheduleNextMonthDto dto = serviceRequestService.getServiceRequestByServiceRequestId(id);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener servicio");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/training-information")
    public ResponseEntity<Object> getTrainingInfo(@RequestParam String serviceRequestId) {
        OutMessage response = new OutMessage();
        try {
            TrainingInformationDto dto = serviceRequestService.getTrainignByServiceRequest(serviceRequestId);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            response.setMessage("Error al obtener informacion de capacitacion");
            response.setDetail(e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

}
