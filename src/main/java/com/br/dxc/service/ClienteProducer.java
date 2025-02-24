package com.br.dxc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteProducer {


    private final RabbitTemplate rabbitTemplate;

    public ClienteProducer(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void enviarMensagem(String mensagem) {
        rabbitTemplate.convertAndSend("clienteQueue", mensagem);
    }
}