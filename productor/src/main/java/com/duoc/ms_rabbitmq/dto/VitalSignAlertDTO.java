package com.duoc.ms_rabbitmq.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class VitalSignAlertDTO {
    private String type;
    private String value;
    private String severity;
    private String message;
}