package com.sofka.co.porteria.BrokerPorteria.Controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/apartamentos")
public class RabbitMQController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/todos")
    public String todos(@RequestParam("exchangeName") String topicExchange,
                          @RequestParam("routingKey") String routingKey,
                          @RequestParam("messageData") String messageData){
        amqpTemplate.convertAndSend(topicExchange, routingKey, messageData);
        return "Mensaje enviado a todos los apartamentos";
    }

    @GetMapping(value = "/impares")
    public String impares(@RequestParam("exchangeName") String topicExchange,
                            @RequestParam("routingKey") String routingKey,
                            @RequestParam("messageData") String messageData){
        amqpTemplate.convertAndSend(topicExchange, routingKey, messageData);
        return "Mensaje enviado a los apartamentos impares";
    }

    @GetMapping(value = "/pares")
    public String pares(@RequestParam("exchangeName") String topicExchange,
                            @RequestParam("routingKey") String routingKey,
                            @RequestParam("messageData") String messageData){
        amqpTemplate.convertAndSend(topicExchange, routingKey, messageData);
        return "Mensaje enviado a los apartamentos pares";
    }

}
