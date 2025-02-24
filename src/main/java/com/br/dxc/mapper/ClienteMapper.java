package com.br.dxc.mapper;

import com.br.dxc.controller.request.ClienteRequest;
import com.br.dxc.controller.response.ClienteResponse;
import com.br.dxc.entitys.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    ClienteResponse toResponse(Cliente cliente);

    Cliente toEntity(ClienteRequest clienteRequest);
}