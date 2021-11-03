package com.accsin.controllers;

import java.util.List;

import com.accsin.models.shared.dto.ScheduleDto;
import com.accsin.services.ScheduleService;
import com.accsin.services.interfaces.ScheduleServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

        scheduleService.testView();
        List<ScheduleDto> listDto =scheduleService.getScheduleNextMonth();
        return ResponseEntity.ok().body(listDto);
    }
}
