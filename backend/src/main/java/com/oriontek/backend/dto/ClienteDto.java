package com.oriontek.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ClienteDto {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

}
