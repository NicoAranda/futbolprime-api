package com.futbolprime.futbolprime_api.dto.marca;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarcaDTO {
    private Long id;
    private String nombre;
    private String slug;
}
