package com.duoc.ms_rabbitmq_cons1.service;

import com.duoc.ms_rabbitmq_cons1.dto.VitalSignsDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.duoc.ms_rabbitmq_cons1.config.RabbitMQConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

@Component
public class RabbitMQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final ObjectMapper objectMapper;
    private final VitalSignsService vitalSignsService;

    @Autowired
    public RabbitMQConsumer(VitalSignsService vitalSignsService) {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.vitalSignsService = vitalSignsService;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_ALERTAS)
    public void consumeMessage(Message message) {
        try {
            String messageBody = new String(message.getBody());
            logger.info("================================================");
            logger.info("Mensaje recibido de la cola de alertas (raw):");
            logger.info(messageBody);
            
            VitalSignsDTO vitalSigns = objectMapper.readValue(messageBody, VitalSignsDTO.class);
            
            logger.info("Mensaje convertido a objeto:");
            logger.info("ID Paciente: {}", vitalSigns.getPatientId());
            logger.info("Ritmo Cardíaco: {}", vitalSigns.getHeartRate());
            logger.info("Presión Sistólica: {}", vitalSigns.getBloodPressureSystolic());
            logger.info("Presión Diastólica: {}", vitalSigns.getBloodPressureDiastolic());
            logger.info("Temperatura: {}", vitalSigns.getBodyTemperature());
            logger.info("Saturación de Oxígeno: {}", vitalSigns.getOxygenSaturation());
            
            // Enviar al endpoint
            vitalSignsService.sendVitalSigns(vitalSigns);
            
            logger.info("================================================");
        } catch (Exception e) {
            logger.error("Error al procesar el mensaje: ", e);
        }
    }
}