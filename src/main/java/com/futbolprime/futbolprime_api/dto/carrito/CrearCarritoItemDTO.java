package com.futbolprime.futbolprime_api.dto.carrito;

import lombok.Data;

@Data
public class CrearCarritoItemDTO {

    private Long usuarioId;
    private Long productoId;
    private Integer cantidad;
}
