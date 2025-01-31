package com.duoc.ms_rabbitmq_prod2.controller;

import com.duoc.ms_rabbitmq_prod2.dto.VitalSignsDTO;
import com.duoc.ms_rabbitmq_prod2.dto.VitalSignsResponseDTO;
import com.duoc.ms_rabbitmq_prod2.service.VitalSignsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/vitalsigns")
public class VitalSignsController {
    private static final Logger logger = LoggerFactory.getLogger(VitalSignsController.class);

    @Autowired
    private VitalSignsService vitalSignsService;

    @PostMapping
    public ResponseEntity<VitalSignsResponseDTO> addVitalSigns(@RequestBody VitalSignsDTO vitalSign) {
        try {
            vitalSignsService.addVitalSign(vitalSign);
            
            VitalSignsResponseDTO response = VitalSignsResponseDTO.builder()
                .patientId(vitalSign.getPatientId())
                .timestamp(vitalSign.getTimestamp())
                .correcto(true)
                .message("Se agregó el signo vital a la lista")
                .build();
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al procesar el registro: {}", e.getMessage(), e);
            
            VitalSignsResponseDTO response = VitalSignsResponseDTO.builder()
                .patientId(vitalSign.getPatientId())
                .timestamp(vitalSign.getTimestamp())
                .correcto(false)
                .message("Error al procesar el signo vital: " + e.getMessage())
                .build();
            
            return ResponseEntity.internalServerError().body(response);
        }
    }
}