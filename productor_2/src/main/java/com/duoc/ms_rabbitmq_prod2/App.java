package com.duoc.ms_rabbitmq_prod2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRabbit
@EnableScheduling
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        logger.info("Microservicio Productor 2 RabbitMQ iniciado correctamente...");
        logger.info("Listo para recibir y publicar mensajes en RabbitMQ Productor 2...");
    }
}