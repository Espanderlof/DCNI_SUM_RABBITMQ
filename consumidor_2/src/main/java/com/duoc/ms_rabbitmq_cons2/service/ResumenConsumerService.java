package com.duoc.ms_rabbitmq_cons2.service;

import com.duoc.ms_rabbitmq_cons2.config.RabbitMQConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class ResumenConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ResumenConsumerService.class);

    @Autowired
    private AzureStorageService azureStorageService;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_RESUMEN)
    public void consumeMessage(Message message) {
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            logger.info("===== MENSAJE RECIBIDO DE LA COLA {} =====", RabbitMQConstants.QUEUE_RESUMEN);
            logger.info("Contenido del mensaje:");
            logger.info(messageBody);
            
            // Guardar en Azure Storage
            azureStorageService.saveJsonToStorage(messageBody);
            
            logger.info("=======================================");
        } catch (Exception e) {
            logger.error("Error al procesar el mensaje: {}", e.getMessage(), e);
        }
    }
}