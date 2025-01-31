package com.duoc.ms_rabbitmq_cons2;

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
        logger.info("Consumidor 2 RabbitMQ iniciado correctamente en puerto 8085");
        logger.info("Listo para consumir mensajes de la cola resumen_alertas");
    }
}