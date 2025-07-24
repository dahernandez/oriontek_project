package com.oriontek.backend.repository;

import com.oriontek.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByNombre(String nombre);

    Optional<Cliente> findByNombreIgnoreCase(String nombre);

    List<Cliente> findByNombreStartingWith(String prefijo);

    List<Cliente> findByNombreContainingIgnoreCase(String fragmento);

    List<Cliente> findByDireccionesIsNotNull();

    List<Cliente> findByDireccionesIsEmpty();

    List<Cliente> findAllByOrderByNombreAsc();

    List<Cliente> findAllByOrderByNombreDesc();

    long countByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
