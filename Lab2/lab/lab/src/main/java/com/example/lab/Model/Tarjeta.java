package com.example.lab.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tarjeta")
@Setter
@Getter

public class Tarjeta {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "numero"
            , length = 50
            , columnDefinition = "varchar(50)")
    private String numero;

    @Column(name = "tipo"
            , length = 10
            , columnDefinition = "varchar(10)")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
}
