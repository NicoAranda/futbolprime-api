package com.futbolprime.futbolprime_api.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private Long id;
    private String sku;
    private String nombre;
    private Integer precio;
    private Integer oferta;
    private Integer stock;
    private String tipo;
    private String talla;
    private String color;
    private String imagen;

    private Long marcaId;
    private String marcaNombre;
}
