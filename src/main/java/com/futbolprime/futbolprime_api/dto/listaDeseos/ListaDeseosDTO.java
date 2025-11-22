package com.futbolprime.futbolprime_api.dto.listaDeseos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ListaDeseosDTO {
    private Long id;
    private Long usuarioId;
    private List<ListaDeseosItemDTO> items;
}
