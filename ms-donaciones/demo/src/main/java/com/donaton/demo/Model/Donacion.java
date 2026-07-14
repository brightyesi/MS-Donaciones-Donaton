package com.donaton.demo.Model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "donacion")
@Data
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDonacion;

    @Column(nullable = false)
    private String recurso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaDonacion categoria;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private String unidad;

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDonacion estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCentro")
    private CentroAcopio centroAcopio;

    private Long donadorId;


    @PrePersist
    protected void onCreate(){
        if (this.fecha==null){
            this.fecha=LocalDate.now();
        }
        if (this.estado== null) {
            this.estado = EstadoDonacion.RECIBIDA;
        }
    }

}
