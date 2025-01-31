package com.duoc.ms_rabbitmq_cons1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@SpringBootApplication
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
            
            // Verificar conexión a RabbitMQ
            RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);
            if (rabbitTemplate != null) {
                logger.info("Conexión a RabbitMQ consumidor 1 establecida correctamente...");
            }
            
            logger.info("El ms consumidor 1 está listo para recibir solicitudes...");
        } catch (Exception e) {
            logger.error("Error al iniciar la aplicación: ", e);
            e.printStackTrace();
        }
    }
}