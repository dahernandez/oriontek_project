package com.oriontek.backend.service.command;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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
    void guardarCliente_debeGuardarClienteCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Lucía");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteCommandService.crearCliente(cliente);

        assertNotNull(resultado);
        assertEquals("Lucía", resultado.getNombre());
        verify(clienteRepository).save(cliente);
    }
}
