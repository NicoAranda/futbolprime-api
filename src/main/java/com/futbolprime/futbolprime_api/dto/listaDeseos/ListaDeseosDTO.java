package com.futbolprime.futbolprime_api.dto.listaDeseos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListaDeseosDTO {
    private Long id;
    private Long usuarioId;
    private Long productoId;
}
