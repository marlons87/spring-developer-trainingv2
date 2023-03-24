package com.example.Labe.springData.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDto {
    private int id;
    private String numero;
    @NotNull(message = "El tipo no puede ser nulo") //Code challenge
    @NotBlank(message = "El valor no puede ser vacio")
    private String tipo;
    private Boolean estado; // activo/desactivo
    private int clienteId;
}
