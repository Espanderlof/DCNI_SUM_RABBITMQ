package com.duoc.ms_rabbitmq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.duoc.ms_rabbitmq.dto.VitalSignsDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VitalSignsService {
    
    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    
    public void analyzeVitalSigns(VitalSignsDTO vitalSigns) {
        StringBuilder alertMessage = new StringBuilder();
        boolean hasAnomaly = false;
        
        // Verificar frecuencia cardíaca (60-100 bpm)
        if (!isHeartRateNormal(vitalSigns.getHeartRate())) {
            String severity = getAlertSeverity(vitalSigns.getHeartRate(), 60.0, 100.0);
            alertMessage.append("Frecuencia cardíaca ").append(severity)
                       .append(": ").append(vitalSigns.getHeartRate()).append(" bpm. ");
            hasAnomaly = true;
        }
        
        // Verificar presión arterial (sistólica: 90-140, diastólica: 60-90)
        if (!isBloodPressureNormal(vitalSigns.getBloodPressureSystolic(), 
                                 vitalSigns.getBloodPressureDiastolic())) {
            alertMessage.append("Presión arterial anormal: ")
                       .append(vitalSigns.getBloodPressureSystolic()).append("/")
                       .append(vitalSigns.getBloodPressureDiastolic()).append(" mmHg. ");
            hasAnomaly = true;
        }
        
        // Verificar temperatura (36.5-37.5°C)
        if (!isTemperatureNormal(vitalSigns.getBodyTemperature())) {
            String severity = getAlertSeverity(vitalSigns.getBodyTemperature(), 36.5, 37.5);
            alertMessage.append("Temperatura corporal ").append(severity)
                       .append(": ").append(vitalSigns.getBodyTemperature()).append("°C. ");
            hasAnomaly = true;
        }
        
        // Verificar saturación de oxígeno (>95%)
        if (!isOxygenSaturationNormal(vitalSigns.getOxygenSaturation())) {
            alertMessage.append("Saturación de oxígeno BAJA: ")
                       .append(vitalSigns.getOxygenSaturation()).append("%. ");
            hasAnomaly = true;
        }
        
        // Si se detecta alguna anomalía, enviar alerta a RabbitMQ
        if (hasAnomaly) {
            String alertHeader = String.format("ALERTA - Paciente ID: %d - %s - ", 
                vitalSigns.getPatientId(),
                vitalSigns.getTimestamp());
            log.info("Anomalía detectada en signos vitales para paciente {}", vitalSigns.getPatientId());
            rabbitMQProducer.sendAlertMessage(alertHeader + alertMessage.toString());
        } else {
            log.info("Signos vitales normales para paciente {}", vitalSigns.getPatientId());
        }
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
    
    private String getAlertSeverity(Double value, Double min, Double max) {
        if (value < min) return "BAJA";
        if (value > max) return "ALTA";
        return "NORMAL";
    }
}