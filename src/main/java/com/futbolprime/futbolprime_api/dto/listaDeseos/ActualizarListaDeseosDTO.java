package com.futbolprime.futbolprime_api.dto.listaDeseos;

import lombok.Data;

@Data
public class ActualizarListaDeseosDTO {
    // Por ahora solo permitimos cambiar el producto
    private Long productoId;
}
