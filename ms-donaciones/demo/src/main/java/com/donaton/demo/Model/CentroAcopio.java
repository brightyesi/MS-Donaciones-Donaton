package com.donaton.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "centroAcopio" )
@Data
public class CentroAcopio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCentro;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private boolean activo;

}
