package com.br.dxc.service;


import com.br.dxc.entitys.Cliente;
import com.br.dxc.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
    }

    @Test
    void deveCriarCliente() {
        when(repository.save(cliente)).thenReturn(cliente);

        Cliente resultado = service.criarCliente(cliente);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(repository, times(1)).save(cliente);
    }

    @Test
    void deveListarClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(repository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = service.listarClientes();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveAtualizarCliente() {
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("Maria Souza");
        clienteAtualizado.setEmail("maria@email.com");

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        Cliente resultado = service.atualizarCliente(1L, clienteAtualizado);

        assertNotNull(resultado);
        assertEquals("Maria Souza", resultado.getNome());
        assertEquals("maria@email.com", resultado.getEmail());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarClienteInexistente() {
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("Maria Souza");
        clienteAtualizado.setEmail("maria@email.com");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.atualizarCliente(1L, clienteAtualizado);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Cliente.class));
    }

    @Test
    void deveExcluirCliente() {
        doNothing().when(repository).deleteById(1L);

        service.excluirCliente(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}