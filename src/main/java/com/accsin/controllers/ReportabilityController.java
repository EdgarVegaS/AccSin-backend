package com.accsin.controllers;

import static com.accsin.utils.DateTimeUtils.getMonthYear;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.accsin.services.ReportabilityService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ReportabilityService reportabilityService;

    @GetMapping
    public ResponseEntity<byte[]> testCreationPDF(@RequestParam String userId, @RequestParam String userName, @RequestParam String startDate, @RequestParam String finishDate){
        try {
            reportabilityService.createServicesPDF(userId, userName, startDate, finishDate);
            byte[] contents = Files.readAllBytes(Paths.get("C:/test/"+userName+"-"+getMonthYear(startDate) +".pdf"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            
            String filename = userName+"-"+getMonthYear(startDate) +".pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            log.error("Error creating PDF report");
        }
        return null;
    }
}
