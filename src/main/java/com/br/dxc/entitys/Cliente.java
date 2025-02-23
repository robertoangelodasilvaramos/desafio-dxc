package com.br.dxc.entitys;

import lombok.*;

import javax.persistence.*;


@Data
@Builder
@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
}
