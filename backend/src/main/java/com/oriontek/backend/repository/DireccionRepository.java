package com.oriontek.backend.repository;

import com.oriontek.backend.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    List<Direccion> findByCiudad(String ciudad);

    List<Direccion> findByCalleContainingIgnoreCase(String calle);

    List<Direccion> findByNumero(String numero);

    List<Direccion> findByClienteId(Long clienteId);

    List<Direccion> findByCiudadAndCalle(String ciudad, String calle);

    List<Direccion> findByCiudadOrCalle(String ciudad, String calle);

    List<Direccion> findByCiudadStartingWithIgnoreCase(String prefijo);

    void deleteByClienteId(Long clienteId);

    long countByClienteId(Long clienteId);

    boolean existsByClienteIdAndCalle(Long clienteId, String calle);
}
