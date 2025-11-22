package com.futbolprime.futbolprime_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false)
    private Integer precio;

    @Column
    private Integer oferta;

    @Column(length = 50)
    private String tipo;

    @Column(length = 20)
    private String talla;

    @Column(length = 50)
    private String color;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    @JsonBackReference
    private Marca marca;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 255)
    private String imagen;
}
