package com.futbolprime.futbolprime_api.dto.carrito;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CarritoDTO {

    private Long id;
    private UUID usuarioId;
    private String estado;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
