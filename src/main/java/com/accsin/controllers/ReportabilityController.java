package com.accsin.controllers;

import static com.accsin.utils.DateTimeUtils.getMonthYear;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.accsin.models.responses.OutMessage;
import com.accsin.models.responses.OutMessage.MessageTipe;
import com.accsin.services.ReportabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/reportability")
public class ReportabilityController {

    @Value("${reportability.base-fodler}")
    private String baseFolder;

    @Autowired
    private ReportabilityService reportabilityService;

    @GetMapping
    public ResponseEntity<?> testCreationPDF(@RequestParam String userId, @RequestParam String userName, @RequestParam String startDate, @RequestParam String finishDate){
        OutMessage responseMsg = new OutMessage();
        try {
            boolean created = reportabilityService.createServicesPDF(userId, userName, startDate, finishDate);
            if (created) {
                byte[] contents = Files.readAllBytes(Paths.get(baseFolder+userId+"/"+userName+"-"+getMonthYear(startDate) +".pdf"));
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                
                String filename = userName+"-"+getMonthYear(startDate) +".pdf";
                headers.setContentDispositionFormData(filename, filename);
                headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
                return response;
            }
            responseMsg.setMessage("Usuario sin registro de solicitudes");
            responseMsg.setDetail("El usuario no ha solicitado servicios para las fechas seleccionadas");
            responseMsg.setMessageTipe(MessageTipe.OK);
            
        } catch (Exception e) {
            responseMsg.setMessage("Error al obtener reporte");
            responseMsg.setMessageTipe(MessageTipe.ERROR);
            log.error("Error creating PDF report. Error: {}",e.getMessage());
        }
        return ResponseEntity.ok().body(responseMsg);
    }
}
