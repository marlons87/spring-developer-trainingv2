package com.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(length = 30)
    private String apellidos;
    @Column(columnDefinition = "varchar(9)")
    private String cedula;
    private String telefono;
    @OneToMany(mappedBy = "cliente")
    private List<Direccion> direccions;
    private String paisNacimiento;

    @OneToMany(mappedBy = "cliente")
    private List<Cuenta> cuentas;

}
