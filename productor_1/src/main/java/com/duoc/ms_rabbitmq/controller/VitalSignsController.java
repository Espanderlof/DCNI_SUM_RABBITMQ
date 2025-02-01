package com.duoc.ms_rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.ms_rabbitmq.dto.VitalSignsDTO;
import com.duoc.ms_rabbitmq.dto.AlertResponseDTO;
import com.duoc.ms_rabbitmq.service.VitalSignsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/vitalsigns")
@Slf4j
@CrossOrigin(origins = "*")
public class VitalSignsController {
    
    @Autowired
    private VitalSignsService vitalSignsService;
    
    @PostMapping
    public ResponseEntity<AlertResponseDTO> receiveVitalSigns(@RequestBody VitalSignsDTO vitalSigns) {
        log.info("Recibiendo signos vitales para paciente ID: {}", vitalSigns.getPatientId());
        AlertResponseDTO response = vitalSignsService.analyzeVitalSigns(vitalSigns);
        return ResponseEntity.ok(response);
    }
}