package com.duoc.ms_rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.duoc.ms_rabbitmq.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VitalSignsService {
    
    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public AlertResponseDTO analyzeVitalSigns(VitalSignsDTO vitalSigns) {
        boolean hasAnomaly = false;
        
        // Verificar frecuencia cardíaca
        if (!isHeartRateNormal(vitalSigns.getHeartRate())) {
            hasAnomaly = true;
        }
        
        // Verificar presión arterial
        if (!isBloodPressureNormal(vitalSigns.getBloodPressureSystolic(), 
                                 vitalSigns.getBloodPressureDiastolic())) {
            hasAnomaly = true;
        }
        
        // Verificar temperatura
        if (!isTemperatureNormal(vitalSigns.getBodyTemperature())) {
            hasAnomaly = true;
        }
        
        // Verificar saturación de oxígeno
        if (!isOxygenSaturationNormal(vitalSigns.getOxygenSaturation())) {
            hasAnomaly = true;
        }
        
        // Si hay anomalías, enviar datos a RabbitMQ
        if (hasAnomaly) {
            try {
                String jsonMessage = objectMapper.writeValueAsString(vitalSigns);
                log.info("Anomalía detectada en signos vitales para paciente {}", vitalSigns.getPatientId());
                rabbitMQProducer.sendAlertMessage(jsonMessage);
            } catch (Exception e) {
                log.error("Error al convertir mensaje a JSON: {}", e.getMessage());
            }
        } else {
            log.info("Signos vitales normales para paciente {}", vitalSigns.getPatientId());
        }
        
        // Construir respuesta simplificada
        return AlertResponseDTO.builder()
            .patientId(vitalSigns.getPatientId())
            .timestamp(vitalSigns.getTimestamp())
            .alertsGenerated(hasAnomaly)
            .message(hasAnomaly ? 
                "Se detectaron anomalías en los signos vitales y se generaron alertas" : 
                "Todos los signos vitales están dentro de los rangos normales")
            .build();
    }
    
    private boolean isHeartRateNormal(Double heartRate) {
        return heartRate != null && heartRate >= 60 && heartRate <= 100;
    }
    
    private boolean isBloodPressureNormal(Double systolic, Double diastolic) {
        return systolic != null && diastolic != null &&
               systolic >= 90 && systolic <= 140 &&
               diastolic >= 60 && diastolic <= 90;
    }
    
    private boolean isTemperatureNormal(Double temperature) {
        return temperature != null && temperature >= 36.5 && temperature <= 37.5;
    }
    
    private boolean isOxygenSaturationNormal(Double saturation) {
        return saturation != null && saturation > 95;
    }
}