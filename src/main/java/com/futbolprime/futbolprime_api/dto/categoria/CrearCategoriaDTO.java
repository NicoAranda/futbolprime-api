package com.futbolprime.futbolprime_api.dto.categoria;

import lombok.Data;

@Data
public class CrearCategoriaDTO {
    private String nombre;
    private String slug;
    private Long padreId;
}
