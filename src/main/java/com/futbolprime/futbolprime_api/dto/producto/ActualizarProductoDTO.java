package com.futbolprime.futbolprime_api.dto.producto;

import lombok.Data;

@Data
public class ActualizarProductoDTO {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer oferta;
    private Integer stock;
    private String tipo;
    private String color;
    private String talla;
    private Long marcaId;
}
