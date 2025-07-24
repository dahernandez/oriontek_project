package com.oriontek.backend.controller;

import com.oriontek.backend.dto.ClienteDto;
import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.service.command.ClienteCommandService;
import com.oriontek.backend.service.query.ClienteQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private ClienteCommandService commandService;
    @Mock
    private ClienteQueryService queryService;

    @InjectMocks
    private ClienteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCliente() {
        ClienteDto dto = new ClienteDto();
        dto.setNombre("Eva");

        Cliente persist = Cliente.builder().id(1L).nombre("Eva").build();
        when(commandService.crearCliente(any())).thenReturn(persist);

        ClienteDto res = controller.crear(dto);

        assertEquals(1L, res.getId());
    }

    @Test
    void listarClientes() {
        when(queryService.obtenerTodos()).thenReturn(List.of(new Cliente()));
        assertEquals(1, controller.listar().size());
    }

    @Test
    void actualizarCliente() {
        ClienteDto dto = new ClienteDto();
        dto.setNombre("Nuevo");

        Cliente updated = Cliente.builder().id(1L).nombre("Nuevo").build();
        when(commandService.actualizarCliente(eq(1L), any())).thenReturn(updated);

        ClienteDto res = controller.actualizar(1L, dto);
        assertEquals("Nuevo", res.getNombre());
    }

    @Test
    void contarClientes() {
        when(queryService.contarClientes()).thenReturn(3L);
        assertEquals(3L, controller.contar());
    }
}
