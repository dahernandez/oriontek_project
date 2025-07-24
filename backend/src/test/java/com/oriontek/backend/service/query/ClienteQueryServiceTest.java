package com.oriontek.backend.service.query;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteQueryServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteQueryService clienteQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contarClientes() {
        when(clienteRepository.count()).thenReturn(10L);
        assertEquals(10L, clienteQueryService.contarClientes());
    }

    @Test
    void obtenerPorId_existente() {
        Cliente c = new Cliente();
        c.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(c));

        Cliente res = clienteQueryService.obtenerPorId(1L);
        assertEquals(1L, res.getId());
    }

    @Test
    void obtenerPorId_noExistente() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> clienteQueryService.obtenerPorId(2L));
    }
}
