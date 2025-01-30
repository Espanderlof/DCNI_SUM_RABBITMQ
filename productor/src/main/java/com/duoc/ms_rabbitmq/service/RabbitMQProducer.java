package com.duoc.ms_rabbitmq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RabbitMQProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        log.info("Mensaje enviado: {}", message);
    }
}
