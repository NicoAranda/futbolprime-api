package com.futbolprime.futbolprime_api.dto;

import lombok.Data;

@Data
public class CrearProductoDTO {

    private String sku;
    private String nombre;
    private Integer precio;
    private Integer oferta;
    private String tipo;
    private String talla;
    private String color;
    private Integer stock;
    private String marca;
    private String descripcion;
    private String imagen;
}
