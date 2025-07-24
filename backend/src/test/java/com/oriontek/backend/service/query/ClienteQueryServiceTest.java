package com.oriontek.backend.service.query;

import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

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
    void obtenerTodos_debeRetornarTodosLosClientes() {
        Cliente c1 = new Cliente();
        c1.setNombre("Ana");
        Cliente c2 = new Cliente();
        c2.setNombre("Luis");

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Cliente> clientes = clienteQueryService.obtenerTodos();

        assertEquals(2, clientes.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void buscarPorNombre_debeRetornarCoincidenciasIgnorandoCase() {
        Cliente c = new Cliente();
        c.setNombre("Pedro");

        when(clienteRepository.findByNombreContainingIgnoreCase("ped")).thenReturn(List.of(c));

        List<Cliente> encontrados = clienteQueryService.buscarPorNombre("ped");

        assertEquals(1, encontrados.size());
        assertEquals("Pedro", encontrados.get(0).getNombre());
    }
}
