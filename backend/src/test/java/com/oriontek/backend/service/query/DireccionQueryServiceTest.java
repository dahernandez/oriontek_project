package com.oriontek.backend.service.query;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.repository.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DireccionQueryServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionQueryService direccionQueryService;

    private Direccion d;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Cliente c = new Cliente();
        c.setId(1L);
        d = Direccion.builder().id(1L).ciudad("SD").calle("A").numero("1").cliente(c).build();
    }

    @Test
    void obtenerTodas() {
        when(direccionRepository.findAll()).thenReturn(List.of(d));
        assertEquals(1, direccionQueryService.obtenerTodas().size());
    }

    @Test
    void obtenerPorId_existente() {
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(d));
        assertEquals("SD", direccionQueryService.obtenerPorId(1L).getCiudad());
    }

    @Test
    void obtenerPorId_noExiste() {
        when(direccionRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> direccionQueryService.obtenerPorId(2L));
    }

    @Test
    void contarPorCliente() {
        when(direccionRepository.countByClienteId(1L)).thenReturn(5L);
        assertEquals(5L, direccionQueryService.contarPorCliente(1L));
    }
}
