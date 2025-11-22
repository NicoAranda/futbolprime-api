package com.futbolprime.futbolprime_api.dto.listaDeseos;

import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.Producto;
import lombok.Data;

@Data
public class ListaDeseosItemDTO {

    private Long id;
    private ProductoDTO producto;
}
