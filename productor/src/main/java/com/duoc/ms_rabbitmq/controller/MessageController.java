package com.duoc.ms_rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.ms_rabbitmq.service.RabbitMQProducer;
import com.duoc.ms_rabbitmq.dto.MessageDTO;

@RestController
@RequestMapping("/api/")
public class MessageController {
    @Autowired
    private RabbitMQProducer producer;
    
    @PostMapping("/message")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        producer.sendMessage(message.getMessage());
        return ResponseEntity.ok("Mensaje enviado a RabbitMQ");
    }
}