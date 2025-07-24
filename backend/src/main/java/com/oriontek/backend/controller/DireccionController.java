package com.oriontek.backend.controller;

import com.oriontek.backend.dto.DireccionDto;
import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.service.command.ClienteCommandService;
import com.oriontek.backend.service.command.DireccionCommandService;
import com.oriontek.backend.service.query.ClienteQueryService;
import com.oriontek.backend.service.query.DireccionQueryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionCommandService commandService;
    private final DireccionQueryService queryService;
    private final ClienteQueryService clienteQueryService;

    public DireccionController(DireccionCommandService commandService, DireccionQueryService queryService, ClienteQueryService clienteQueryService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.clienteQueryService = clienteQueryService;
    }

    @PostMapping
    public DireccionDto crear(@Valid @RequestBody DireccionDto dto) {
        Cliente cliente = clienteQueryService.obtenerPorId(dto.getClienteId());

        Direccion direccion = Direccion.builder()
                .ciudad(dto.getCiudad())
                .calle(dto.getCalle())
                .numero(dto.getNumero())
                .cliente(cliente)
                .build();

        Direccion guardada = commandService.crearDireccion(direccion);
        dto.setId(guardada.getId());
        return dto;
    }

    @PutMapping("/{id}")
    public DireccionDto actualizar(@PathVariable Long id, @Valid @RequestBody DireccionDto dto) {
        Cliente cliente = clienteQueryService.obtenerPorId(dto.getClienteId());

        Direccion direccion = Direccion.builder()
                .ciudad(dto.getCiudad())
                .calle(dto.getCalle())
                .numero(dto.getNumero())
                .cliente(cliente)
                .build();

        Direccion actualizada = commandService.actualizarDireccion(id, direccion);
        dto.setId(actualizada.getId());
        return dto;
    }

    @GetMapping("/{id}")
    public DireccionDto obtenerPorId(@PathVariable Long id) {
        Direccion d = queryService.obtenerPorId(id);
        DireccionDto dto = new DireccionDto();
        dto.setId(d.getId());
        dto.setCiudad(d.getCiudad());
        dto.setCalle(d.getCalle());
        dto.setNumero(d.getNumero());
        dto.setClienteId(d.getCliente().getId());
        return dto;
    }

    @GetMapping("/cliente/{clienteId}")
    public List<DireccionDto> porCliente(@PathVariable Long clienteId) {
        return queryService.obtenerPorClienteId(clienteId).stream().map(d -> {
            DireccionDto dto = new DireccionDto();
            dto.setId(d.getId());
            dto.setCiudad(d.getCiudad());
            dto.setCalle(d.getCalle());
            dto.setNumero(d.getNumero());
            dto.setClienteId(clienteId);
            return dto;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        commandService.eliminarDireccion(id);
    }

    @GetMapping
    public List<DireccionDto> listar() {
        return queryService.obtenerTodas().stream().map(d -> {
            DireccionDto dto = new DireccionDto();
            dto.setId(d.getId());
            dto.setCiudad(d.getCiudad());
            dto.setCalle(d.getCalle());
            dto.setNumero(d.getNumero());
            dto.setClienteId(d.getCliente().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/count/cliente/{clienteId}")
    public long contarPorCliente(@PathVariable Long clienteId) {
        return queryService.contarPorCliente(clienteId);
    }

    @GetMapping("/buscar")
    public List<DireccionDto> buscarPorCiudadOCalle(@RequestParam String ciudad, @RequestParam String calle) {
        return queryService.buscarPorCiudadOCalle(ciudad, calle).stream().map(d -> {
            DireccionDto dto = new DireccionDto();
            dto.setId(d.getId());
            dto.setCiudad(d.getCiudad());
            dto.setCalle(d.getCalle());
            dto.setNumero(d.getNumero());
            dto.setClienteId(d.getCliente().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}
