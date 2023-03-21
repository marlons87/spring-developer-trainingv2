package com.example.lab.Model;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cliente")
@Setter
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre"
            , length = 50
            , columnDefinition = "varchar(50)")
    private String nombre;

    @Column(name = "apellidos"
            , length = 50
            , columnDefinition = "varchar(50)")
    private String apellidos;

    @Column(name = "cedula"
            , length = 15
            , columnDefinition = "varchar(15)")
    private String cedula;

    @Column(name = "telefono"
            , length = 11
            , columnDefinition = "varchar(11)")
    private String telefono;


    @Column(name = "telefono"
            , length = 11
            , columnDefinition = "varchar(11)")
    private String tipo;


    @Column(name = "pais"
            , length = 11
            , columnDefinition = "varchar(50)")
    private String pais;



    @OneToMany(mappedBy = "cliente"
            , cascade = CascadeType.ALL
            , fetch = FetchType.LAZY
            , orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();

    @OneToMany(mappedBy = "cliente"
            , cascade = CascadeType.ALL
            , fetch = FetchType.LAZY
            , orphanRemoval = true)
    private List<Cuenta> cuentas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente"
            , cascade = CascadeType.ALL
            , fetch = FetchType.LAZY
            , orphanRemoval = true)
    private List<Tarjeta> tarjetas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente"
            , cascade = CascadeType.ALL
            , fetch = FetchType.LAZY
            , orphanRemoval = true)
    private List<Inversion> inversiones = new ArrayList<>();

}