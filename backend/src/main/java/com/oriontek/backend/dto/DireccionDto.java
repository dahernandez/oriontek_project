package com.oriontek.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DireccionDto {
    private Long id;

    @NotBlank
    private String ciudad;

    @NotBlank
    private String calle;

    @NotBlank
    private String numero;

    private Long clienteId;
}
