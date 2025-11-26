package com.futbolprime.futbolprime_api.dto.pedido;

import lombok.Data;

import java.util.List;

@Data
public class CrearPedidoDTO {

    private Long usuarioId;
    private List<CrearPedidoItemDTO> items;

    private Integer envio;
    private Integer descuento;

    private String dirNombre;
    private String dirLinea1;
    private String dirLinea2;
    private String dirCiudad;
    private String dirRegion;
    private String dirZip;
    private String dirPais;
    private String dirTelefono;
}
