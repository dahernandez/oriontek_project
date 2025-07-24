package com.oriontek.backend.service.command;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteCommandService {

    private final ClienteRepository clienteRepository;

    public ClienteCommandService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.existsByNombre(cliente.getNombre())) {
            throw new IllegalArgumentException("Ya existe un cliente con el nombre: " + cliente.getNombre());
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizarCliente(Long id, Cliente nuevoCliente) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(nuevoCliente.getNombre());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Transactional
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
