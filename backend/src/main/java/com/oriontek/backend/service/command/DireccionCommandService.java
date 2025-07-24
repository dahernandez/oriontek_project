package com.oriontek.backend.service.command;

import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.repository.DireccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DireccionCommandService {

    private final DireccionRepository direccionRepository;

    public DireccionCommandService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @Transactional
    public Direccion crearDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    @Transactional
    public Direccion actualizarDireccion(Long id, Direccion nuevaDireccion) {
        return direccionRepository.findById(id)
                .map(direccion -> {
                    direccion.setCiudad(nuevaDireccion.getCiudad());
                    direccion.setCalle(nuevaDireccion.getCalle());
                    direccion.setNumero(nuevaDireccion.getNumero());
                    return direccionRepository.save(direccion);
                })
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
    }

    @Transactional
    public void eliminarDireccion(Long id) {
        direccionRepository.deleteById(id);
    }

    @Transactional
    public void eliminarPorClienteId(Long clienteId) {
        direccionRepository.deleteByClienteId(clienteId);
    }
}
