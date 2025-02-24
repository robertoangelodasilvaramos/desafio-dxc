package com.br.dxc.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClienteProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ClienteProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensagem(String mensagem) {
        rabbitTemplate.convertAndSend("clienteQueue", mensagem);
    }
}