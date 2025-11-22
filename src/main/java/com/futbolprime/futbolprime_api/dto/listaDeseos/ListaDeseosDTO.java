package com.futbolprime.futbolprime_api.dto.listaDeseos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListaDeseosDTO {
    private Long id;
    private String usuarioId;
    private Long productoId;
    private LocalDateTime creadoEn;
}
