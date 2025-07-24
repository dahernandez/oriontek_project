package com.oriontek.backend.service.command;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.repository.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DireccionCommandServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionCommandService direccionCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarDireccion_debeGuardarYRetornar() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Direccion direccion = new Direccion();
        direccion.setCiudad("Santiago");
        direccion.setCliente(cliente);

        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

        Direccion resultado = direccionCommandService.crearDireccion(direccion);

        assertNotNull(resultado);
        assertEquals("Santiago", resultado.getCiudad());
        verify(direccionRepository).save(direccion);
    }
}
