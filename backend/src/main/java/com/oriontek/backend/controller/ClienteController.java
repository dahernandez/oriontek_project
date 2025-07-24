package com.oriontek.backend.controller;

import com.oriontek.backend.dto.ClienteDto;
import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.service.command.ClienteCommandService;
import com.oriontek.backend.service.query.ClienteQueryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteCommandService commandService;
    private final ClienteQueryService queryService;

    public ClienteController(ClienteCommandService commandService, ClienteQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ClienteDto crear(@Valid @RequestBody ClienteDto dto) {
        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .build();

        Cliente guardado = commandService.crearCliente(cliente);
        dto.setId(guardado.getId());
        return dto;
    }

    @PutMapping("/{id}")
    public ClienteDto actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDto dto) {
        Cliente cliente = Cliente.builder()
                .nombre(dto.getNombre())
                .build();

        Cliente actualizado = commandService.actualizarCliente(id, cliente);
        dto.setId(actualizado.getId());
        return dto;
    }

    @GetMapping
    public List<ClienteDto> listar() {
        return queryService.obtenerTodos().stream().map(c -> {
            ClienteDto dto = new ClienteDto();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClienteDto obtener(@PathVariable Long id) {
        Cliente c = queryService.obtenerPorId(id);
        ClienteDto dto = new ClienteDto();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        return dto;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        commandService.eliminarCliente(id);
    }

    @GetMapping("/count")
    public long contar() {
        return queryService.contarClientes();
    }

    @GetMapping("/buscar/prefijo/{prefijo}")
    public List<ClienteDto> buscarPorPrefijo(@PathVariable String prefijo) {
        return queryService.buscarPorPrefijoNombre(prefijo).stream().map(c -> {
            ClienteDto dto = new ClienteDto();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/con-direcciones")
    public long contarClientesConDirecciones() {
        return queryService.contarClientesConDirecciones();
    }
}

