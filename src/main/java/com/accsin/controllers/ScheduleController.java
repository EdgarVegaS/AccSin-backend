package com.accsin.controllers;

import java.util.List;

import com.accsin.models.responses.OutMessage;
import com.accsin.models.shared.dto.DateAvailableDto;
import com.accsin.services.interfaces.ScheduleServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/schedule")
public class ScheduleController {
    
    @Autowired
    ScheduleServiceInterface scheduleService;

    /*@PostMapping
    public ResponseEntity<Object> createSchedule(@RequestBody CreateScheduleRequest request){

    }*/

    @GetMapping("/get-schedule-next-month")
    public ResponseEntity<Object> getScheduleNextMonth(){

        OutMessage response = new OutMessage();
        try {
            List<DateAvailableDto> listDto = scheduleService.getAvailableDays();
            return ResponseEntity.ok().body(listDto);
        } catch (Exception e) {
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
			response.setMessage("Se produjo un error obteniendo la lista de fechas disponibles");
			response.setDetail(e.getMessage());
			return ResponseEntity.ok().body(response);
        }
    }
}
