package com.br.dxc.controller.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nome;
    private String email;
}
