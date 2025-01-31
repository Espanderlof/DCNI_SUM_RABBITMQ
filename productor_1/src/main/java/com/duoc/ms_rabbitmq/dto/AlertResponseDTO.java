package com.duoc.ms_rabbitmq.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class AlertResponseDTO {
    private Long patientId;
    private LocalDateTime timestamp;
    private boolean alertsGenerated;
    private String message;
}