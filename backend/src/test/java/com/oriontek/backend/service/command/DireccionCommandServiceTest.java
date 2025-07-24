package com.oriontek.backend.service.command;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.repository.DireccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DireccionCommandServiceTest {

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private DireccionCommandService direccionCommandService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
    }

    @Test
    void crearDireccion_conCamposValidos() {
        Direccion direccion = Direccion.builder()
                .ciudad("Santiago").calle("Principal").numero("10").cliente(cliente).build();

        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

        Direccion resultado = direccionCommandService.crearDireccion(direccion);

        assertNotNull(resultado);
        verify(direccionRepository).save(direccion);
    }

    @Test
    void crearDireccion_fallaSiCiudadVacia() {
        Direccion direccion = Direccion.builder()
                .calle("X").numero("1").cliente(cliente).build();

        assertThrows(IllegalArgumentException.class,
                () -> direccionCommandService.crearDireccion(direccion));
    }

    @Test
    void actualizarDireccion_debeActualizarCampos() {
        Direccion existente = Direccion.builder()
                .id(1L).ciudad("Vieja").calle("A").numero("1").cliente(cliente).build();
        Direccion nueva = Direccion.builder()
                .ciudad("Nueva").calle("B").numero("2").cliente(cliente).build();

        when(direccionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(direccionRepository.save(any(Direccion.class))).thenReturn(existente);

        Direccion res = direccionCommandService.actualizarDireccion(1L, nueva);

        assertEquals("Nueva", res.getCiudad());
        assertEquals("B", res.getCalle());
        assertEquals("2", res.getNumero());
    }

    @Test
    void eliminarDireccion_invocaDeleteById() {
        doNothing().when(direccionRepository).deleteById(2L);
        direccionCommandService.eliminarDireccion(2L);
        verify(direccionRepository).deleteById(2L);
    }

    @Test
    void eliminarPorClienteId_invocaDeleteByClienteId() {
        doNothing().when(direccionRepository).deleteByClienteId(1L);
        direccionCommandService.eliminarPorClienteId(1L);
        verify(direccionRepository).deleteByClienteId(1L);
    }
}
