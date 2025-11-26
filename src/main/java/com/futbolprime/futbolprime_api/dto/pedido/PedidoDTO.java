package com.futbolprime.futbolprime_api.dto.pedido;

import lombok.Data;

import java.util.List;

@Data
public class PedidoDTO {

    private Long id;
    private Long usuarioId;
    private String estado;
    private Integer subtotal;
    private Integer envio;
    private Integer descuento;
    private Integer total;

    private String dirNombre;
    private String dirLinea1;
    private String dirLinea2;
    private String dirCiudad;
    private String dirRegion;
    private String dirZip;
    private String dirPais;
    private String dirTelefono;

    private List<PedidoItemDTO> items;
}
