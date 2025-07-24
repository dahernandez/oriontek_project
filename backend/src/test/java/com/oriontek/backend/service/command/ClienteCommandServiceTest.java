package com.oriontek.backend.service.command;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteCommandServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteCommandService clienteCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCliente_debeGuardarClienteCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Lucía");

        when(clienteRepository.existsByNombre("Lucía")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteCommandService.crearCliente(cliente);

        assertNotNull(resultado);
        assertEquals("Lucía", resultado.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void crearCliente_debeLanzarErrorSiExisteNombreDuplicado() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Lucía");

        when(clienteRepository.existsByNombre("Lucía")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> clienteCommandService.crearCliente(cliente));
    }

    @Test
    void actualizarCliente_debeActualizarNombre() {
        Cliente existente = new Cliente();
        existente.setId(1L);
        existente.setNombre("Juan");

        Cliente nuevo = new Cliente();
        nuevo.setNombre("Pedro");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(existente);

        Cliente resultado = clienteCommandService.actualizarCliente(1L, nuevo);

        assertEquals("Pedro", resultado.getNombre());
        verify(clienteRepository).save(existente);
    }

    @Test
    void eliminarCliente_debeInvocarDeleteById() {
        doNothing().when(clienteRepository).deleteById(1L);
        clienteCommandService.eliminarCliente(1L);
        verify(clienteRepository).deleteById(1L);
    }
}
