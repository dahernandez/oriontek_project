package com.oriontek.backend.service.query;

import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionQueryService {

    private final DireccionRepository direccionRepository;

    public DireccionQueryService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    public List<Direccion> obtenerTodas() {
        return direccionRepository.findAll();
    }

    public Direccion obtenerPorId(Long id) {
        return direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    }

    public List<Direccion> buscarPorCiudad(String ciudad) {
        return direccionRepository.findByCiudadContainingIgnoreCase(ciudad);
    }

    public List<Direccion> obtenerPorClienteId(Long clienteId) {
        return direccionRepository.findByClienteId(clienteId);
    }

    public long contarPorCliente(Long clienteId) {
        return direccionRepository.countByClienteId(clienteId);
    }
}
