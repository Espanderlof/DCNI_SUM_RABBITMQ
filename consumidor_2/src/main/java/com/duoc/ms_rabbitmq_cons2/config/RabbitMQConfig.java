package com.duoc.ms_rabbitmq_cons2.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue resumenQueue() {
        return new Queue(RabbitMQConstants.QUEUE_RESUMEN, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE_NAME);
    }

    @Bean
    public Binding resumenBinding(Queue resumenQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(resumenQueue)
                .to(exchange)
                .with(RabbitMQConstants.ROUTING_KEY_RESUMEN);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}