package com.duoc.ms_rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableRabbit
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        logger.info("Microservicio Productor RabbitMQ iniciado correctamente");
        logger.info("Listo para recibir y publicar mensajes en http://localhost:8080/api/message");
    }
}