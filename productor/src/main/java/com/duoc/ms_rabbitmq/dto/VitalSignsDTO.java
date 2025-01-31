package com.duoc.ms_rabbitmq.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VitalSignsDTO {
    private Long patientId;
    private Double heartRate;
    private Double bloodPressureSystolic;
    private Double bloodPressureDiastolic;
    private Double bodyTemperature;
    private Double oxygenSaturation;
    private LocalDateTime timestamp;
}