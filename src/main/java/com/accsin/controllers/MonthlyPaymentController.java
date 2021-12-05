package com.accsin.controllers;

import java.util.List;

import com.accsin.models.responses.OutMessage;
import com.accsin.models.shared.dto.UserMonthlyPymentDto;
import com.accsin.services.MonthlyPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monthly-payment")
public class MonthlyPaymentController {
    
    @Autowired
    private MonthlyPaymentService monthlyPaymentService;

    @GetMapping("/user")
    public ResponseEntity<Object> getByUser(@RequestParam String userId){
        OutMessage response = new OutMessage();
        try {
            List<UserMonthlyPymentDto> ListDto =  monthlyPaymentService.getMonthlyPaymentByUser(userId);
            return ResponseEntity.ok().body(ListDto);
        } catch (Exception e) {
            response.setMessage("Error obteniendo listado de pagos mensuales");
            response.setDetail(e.getMessage());
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
            
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/payment")
    public ResponseEntity<Object> createPayment(@RequestParam String monthlyPaymentId){
        OutMessage response = new OutMessage();
        try {
            monthlyPaymentService.updateMonthlyPayment(monthlyPaymentId);
            response.setMessage("Pago mensual actualizado con exito");
            response.setDetail("");
            response.setMessageTipe(OutMessage.MessageTipe.OK);

        } catch (Exception e) {
            response.setDetail(e.getMessage());
            response.setMessage("Error al actualizar pago mensual");
            response.setMessageTipe(OutMessage.MessageTipe.ERROR);
        }
        return ResponseEntity.ok().body(response);
    }

}