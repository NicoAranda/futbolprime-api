package com.futbolprime.futbolprime_api.dto.carrito;

import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import lombok.Data;

@Data
public class CarritoItemDTO {

    private Long id;
    private ProductoDTO producto;
    private Integer cantidad;
    private Integer precioUnitSnap;
}
