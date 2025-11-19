package com.futbolprime.futbolprime_api.dto;

import lombok.Data;

@Data
public class ProductoDTO {

    private String sku;
    private String nombre;
    private Integer precio;
    private Integer oferta;
    private Integer stock;
    private String tipo;
    private String talla;
    private String color;
    private String marca;
    private String imagen;
}
