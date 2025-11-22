package com.futbolprime.futbolprime_api.dto.carrito;

import lombok.Data;
import java.util.UUID;

@Data
public class CrearCarritoDTO {

    private UUID usuarioId;
    private String estado;
}
