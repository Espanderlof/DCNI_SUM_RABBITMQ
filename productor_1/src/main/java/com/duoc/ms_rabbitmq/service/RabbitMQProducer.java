package com.duoc.ms_rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.duoc.ms_rabbitmq.config.RabbitMQConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendAlertMessage(String message) {
        rabbitTemplate.convertAndSend(
            RabbitMQConstants.EXCHANGE_NAME, 
            RabbitMQConstants.ROUTING_KEY_ALERTAS, 
            message
        );
        log.info("Alerta enviada: {}", message);
    }
    
    public void sendResumenMessage(String message) {
        rabbitTemplate.convertAndSend(
            RabbitMQConstants.EXCHANGE_NAME, 
            RabbitMQConstants.ROUTING_KEY_RESUMEN, 
            message
        );
        log.info("Resumen enviado: {}", message);
    }
}