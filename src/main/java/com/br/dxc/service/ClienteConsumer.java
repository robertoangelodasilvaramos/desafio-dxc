package com.br.dxc.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ClienteConsumer {

    @RabbitListener(queues = "clienteQueue")
    public void consumirMensagem(String mensagem) {
        System.out.println("Mensagem Recebida: " + mensagem);
    }
}
