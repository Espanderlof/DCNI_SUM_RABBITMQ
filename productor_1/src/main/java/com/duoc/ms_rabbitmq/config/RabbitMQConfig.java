package com.duoc.ms_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue queueAlertas() {
        return new Queue(RabbitMQConstants.QUEUE_ALERTAS);
    }

    @Bean
    public Queue queueResumen() {
        return new Queue(RabbitMQConstants.QUEUE_RESUMEN);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE_NAME);
    }

    @Bean
    public Binding bindingAlertas() {
        return BindingBuilder
            .bind(queueAlertas())
            .to(exchange())
            .with(RabbitMQConstants.ROUTING_KEY_ALERTAS);
    }

    @Bean
    public Binding bindingResumen() {
        return BindingBuilder
            .bind(queueResumen())
            .to(exchange())
            .with(RabbitMQConstants.ROUTING_KEY_RESUMEN);
    }
}