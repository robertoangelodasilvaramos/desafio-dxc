package com.br.dxc.controller;

import com.br.dxc.controller.request.ClienteRequest;
import com.br.dxc.controller.response.ClienteResponse;
import com.br.dxc.entitys.Cliente;
import com.br.dxc.mapper.ClienteMapper;
import com.br.dxc.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService service;

    @Mock
    private ClienteMapper mapper;

    @InjectMocks
    private ClienteController controller;

    private Cliente cliente;
    private ClienteRequest clienteRequest;
    private ClienteResponse clienteResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");

        clienteRequest = new ClienteRequest();
        clienteRequest.setNome("João Silva");
        clienteRequest.setEmail("joao@email.com");

        clienteResponse = new ClienteResponse();
        clienteResponse.setId(1L);
        clienteResponse.setNome("João Silva");
        clienteResponse.setEmail("joao@email.com");
    }

    @Test
    void deveCriarCliente() throws Exception {
        when(mapper.toEntity(any(ClienteRequest.class))).thenReturn(cliente);
        when(service.criarCliente(any(Cliente.class))).thenReturn(cliente);
        when(mapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(service, times(1)).criarCliente(any(Cliente.class));
    }

    @Test
    void deveListarClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(service.listarClientes()).thenReturn(clientes);

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(service, times(1)).listarClientes();
    }

    @Test
    void deveBuscarPorId() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(mapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);

        mockMvc.perform(get("/clientes/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void deveRetornarNotFoundAoBuscarClienteInexistente() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void deveAtualizarCliente() throws Exception {
        when(mapper.toEntity(any(ClienteRequest.class))).thenReturn(cliente);
        when(service.atualizarCliente(eq(1L), any(Cliente.class))).thenReturn(cliente);
        when(mapper.toResponse(any(Cliente.class))).thenReturn(clienteResponse);

        mockMvc.perform(put("/clientes/"+ 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clienteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(service, times(1)).atualizarCliente(eq(1L), any(Cliente.class));
    }

    @Test
    void deveExcluirCliente() throws Exception {
        doNothing().when(service).excluirCliente(1L);

        mockMvc.perform(delete("/clientes/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).excluirCliente(1L);
    }
}
