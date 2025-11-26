package com.futbolprime.futbolprime_api.dto.carrito;

import com.futbolprime.futbolprime_api.model.CarritoItem;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CarritoDTO {

    private Long id;
    private Long usuarioId;
    private String estado;
    private List<CarritoItemDTO> items;
}
