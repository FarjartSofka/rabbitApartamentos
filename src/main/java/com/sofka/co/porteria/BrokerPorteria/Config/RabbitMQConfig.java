package com.sofka.co.porteria.BrokerPorteria.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue apartamentos(){
        return new Queue("apartamentos", true);
    }

    @Bean
    Queue apartamentosImpares(){
        return new Queue("apartamentosImpares", true);
    }

    @Bean
    Queue apartamentosPares(){
        return new Queue("apartamentosPares", true);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("apartamentos");
    }

    @Bean
    Binding apartamentosImparesBinding(Queue apartamentosImpares, TopicExchange topicExchange){
        return BindingBuilder.bind(apartamentosImpares).to(topicExchange).with("apartamentos.impar");
    }

    @Bean
    Binding apartamentosBinding(Queue apartamentos, TopicExchange topicExchange){
        return BindingBuilder.bind(apartamentos).to(topicExchange).with("apartamentos.*");
    }

    @Bean
    Binding apartamentosParesBinding(Queue apartamentosPares, TopicExchange topicExchange){
        return BindingBuilder.bind(apartamentosPares).to(topicExchange).with("apartamentos.par");
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate =new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}