package com.duoc.ms_rabbitmq_prod2.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class VitalSignsResponseDTO {
    private Long patientId;
    private LocalDateTime timestamp;
    private boolean correcto;
    private String message;
}