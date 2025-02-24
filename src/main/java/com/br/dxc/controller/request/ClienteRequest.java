package com.br.dxc.controller.request;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {

    private Long id;
    private String nome;
    private String email;
}
