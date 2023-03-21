package com.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClienteDto {
    private int id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private List<DireccionDto> direccionsDto;
    private String paisNacimiento;
    private List<CuentaDto> cuentas;
}