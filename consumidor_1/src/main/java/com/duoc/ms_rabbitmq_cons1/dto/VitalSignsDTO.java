package com.duoc.ms_rabbitmq_cons1.dto;

import lombok.Data;

@Data
public class VitalSignsDTO {
    private Long patientId;
    private Double heartRate;
    private Double bloodPressureSystolic;
    private Double bloodPressureDiastolic;
    private Double bodyTemperature;
    private Double oxygenSaturation;
}