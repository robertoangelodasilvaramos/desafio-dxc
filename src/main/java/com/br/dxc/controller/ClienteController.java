package com.br.dxc.controller;

import com.br.dxc.controller.request.ClienteRequest;
import com.br.dxc.controller.response.ClienteResponse;
import com.br.dxc.entitys.Cliente;
import com.br.dxc.mapper.ClienteMapper;
import com.br.dxc.service.ClienteService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService service;
    private ClienteMapper mapper;

    public ClienteController(ClienteService service, ClienteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criarCliente(@RequestBody ClienteRequest cliente) {
        return ResponseEntity.ok(mapper.toResponse(service.criarCliente(mapper.toEntity(cliente))));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(service.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest cliente) {
        return ResponseEntity.ok(mapper.toResponse(service.atualizarCliente(id, mapper.toEntity(cliente))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        service.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }
}
