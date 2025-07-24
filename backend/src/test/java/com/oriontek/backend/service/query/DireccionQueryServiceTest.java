package com.oriontek.backend.service.query;

import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.repository.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DireccionQueryServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionQueryService direccionQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorClienteId_debeRetornarDirecciones() {
        Direccion d = new Direccion();
        d.setCiudad("La Vega");

        when(direccionRepository.findByClienteId(1L)).thenReturn(List.of(d));

        List<Direccion> direcciones = direccionQueryService.obtenerPorClienteId(1L);

        assertEquals(1, direcciones.size());
        assertEquals("La Vega", direcciones.get(0).getCiudad());
    }

    @Test
    void buscarPorCiudad_debeBuscarIgnorandoCase() {
        Direccion d = new Direccion();
        d.setCiudad("Punta Cana");

        when(direccionRepository.findByCiudadContainingIgnoreCase("punta")).thenReturn(List.of(d));

        List<Direccion> resultado = direccionQueryService.buscarPorCiudad("punta");

        assertEquals(1, resultado.size());
        assertEquals("Punta Cana", resultado.get(0).getCiudad());
    }
}
