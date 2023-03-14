package ru.otus.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ru.otus.crm.service.RabbitMqServiceImpl.MAIN_EXCHANGE;


@Configuration
public class RabbitMqConfig {

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(15000);
        rabbitTemplate.setMessageConverter(jsonConverter());
        rabbitTemplate.setExchange(MAIN_EXCHANGE);
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MAIN_EXCHANGE, false, true);
    }

    @Bean
    public Queue approvalResultsEventsQueue(){
        return new Queue("approval-results-queue");
    }

    @Bean
    public Binding approvalResultsEventsQueueBinding(){
        return BindingBuilder.bind(approvalResultsEventsQueue())
                .to(topicExchange())
                .with("document.hash512");
    }

}