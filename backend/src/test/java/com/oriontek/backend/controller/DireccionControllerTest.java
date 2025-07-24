package com.oriontek.backend.controller;

import com.oriontek.backend.dto.DireccionDto;
import com.oriontek.backend.model.Cliente;
import com.oriontek.backend.model.Direccion;
import com.oriontek.backend.service.command.DireccionCommandService;
import com.oriontek.backend.service.query.ClienteQueryService;
import com.oriontek.backend.service.query.DireccionQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DireccionControllerTest {

    @Mock
    private DireccionCommandService commandService;
    @Mock
    private DireccionQueryService queryService;
    @Mock
    private ClienteQueryService clienteQueryService;

    @InjectMocks
    private DireccionController controller;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = Cliente.builder().id(1L).nombre("Eva").build();
    }

    @Test
    void crearDireccion() {
        DireccionDto dto = new DireccionDto();
        dto.setCiudad("SD"); dto.setCalle("calle"); dto.setNumero("10"); dto.setClienteId(1L);

        when(clienteQueryService.obtenerPorId(1L)).thenReturn(cliente);
        Direccion pers = Direccion.builder().id(5L).ciudad("SD").calle("calle").numero("10").cliente(cliente).build();
        when(commandService.crearDireccion(any())).thenReturn(pers);

        DireccionDto res = controller.crear(dto);

        assertEquals(5L, res.getId());
    }

    @Test
    void listarDirecciones() {
        Direccion d = Direccion.builder().id(1L).ciudad("SD").calle("A").numero("1").cliente(cliente).build();
        when(queryService.obtenerTodas()).thenReturn(List.of(d));
        assertEquals(1, controller.listar().size());
    }

    @Test
    void contarPorCliente() {
        when(queryService.contarPorCliente(1L)).thenReturn(2L);
        assertEquals(2L, controller.contarPorCliente(1L));
    }
}
