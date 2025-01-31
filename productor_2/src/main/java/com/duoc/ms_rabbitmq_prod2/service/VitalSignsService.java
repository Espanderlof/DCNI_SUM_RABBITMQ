package com.duoc.ms_rabbitmq_prod2.service;

import com.duoc.ms_rabbitmq_prod2.config.RabbitMQConstants;
import com.duoc.ms_rabbitmq_prod2.dto.VitalSignsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VitalSignsService {
    private static final Logger logger = LoggerFactory.getLogger(VitalSignsService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Usamos CopyOnWriteArrayList para thread-safety
    private final List<VitalSignsDTO> vitalSignsBuffer = new CopyOnWriteArrayList<>();

    public void addVitalSign(VitalSignsDTO vitalSign) {
        vitalSignsBuffer.add(vitalSign);
        logger.info("Nuevo registro de signos vitales añadido para el paciente {}. Total registros en buffer: {}", 
            vitalSign.getPatientId(), vitalSignsBuffer.size());
    }

    @Scheduled(fixedRate = 300000) // 5 minutos = 300000 ms
    public void processAndSendVitalSigns() {
        if (vitalSignsBuffer.isEmpty()) {
            logger.info("No hay registros para enviar en este momento");
            return;
        }

        try {
            // Creamos una nueva lista con los datos actuales
            List<VitalSignsDTO> dataToSend = new ArrayList<>(vitalSignsBuffer);
            
            // Limpiamos el buffer
            vitalSignsBuffer.clear();
            
            logger.info("Enviando {} registros de signos vitales a RabbitMQ", dataToSend.size());
            
            rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE_NAME,
                RabbitMQConstants.ROUTING_KEY_RESUMEN,
                dataToSend
            );
            
            logger.info("Datos enviados exitosamente a la cola {}", RabbitMQConstants.QUEUE_RESUMEN);
        } catch (Exception e) {
            logger.error("Error al enviar los datos a RabbitMQ: {}", e.getMessage(), e);
            // En caso de error, intentamos preservar los datos
            if (!vitalSignsBuffer.isEmpty()) {
                logger.info("Manteniendo {} registros en el buffer para el próximo intento", vitalSignsBuffer.size());
            }
        }
    }
}