package com.futbolprime.futbolprime_api.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "CrearPedidoDTO",
        description = "DTO utilizado para crear un nuevo pedido, incluyendo usuario, ítems y dirección de envío."
)
public class CrearPedidoDTO {

    @Schema(
            description = "ID del usuario que realiza el pedido",
            example = "12",
            required = true
    )
    private Long usuarioId;

    @Schema(
            description = "Lista de ítems que componen el pedido",
            implementation = CrearPedidoItemDTO.class,
            required = true
    )
    private List<CrearPedidoItemDTO> items;

    @Schema(
            description = "Costo del envío en CLP",
            example = "3990"
    )
    private Integer envio;

    @Schema(
            description = "Monto de descuento aplicado al pedido",
            example = "2000"
    )
    private Integer descuento;

    @Schema(
            description = "Nombre completo del destinatario",
            example = "Fernanda Soto"
    )
    private String dirNombre;

    @Schema(
            description = "Línea 1 de la dirección del envío (calle y número)",
            example = "Av. Las Torres 1234"
    )
    private String dirLinea1;

    @Schema(
            description = "Línea 2 opcional de la dirección (departamento, oficina, etc.)",
            example = "Dpto 504",
            nullable = true
    )
    private String dirLinea2;

    @Schema(
            description = "Ciudad del envío",
            example = "Santiago"
    )
    private String dirCiudad;

    @Schema(
            description = "Región o estado del envío",
            example = "Región Metropolitana"
    )
    private String dirRegion;

    @Schema(
            description = "Código postal",
            example = "9020000"
    )
    private String dirZip;

    @Schema(
            description = "País del envío",
            example = "Chile"
    )
    private String dirPais;

    @Schema(
            description = "Número de teléfono del destinatario",
            example = "+56987654321"
    )
    private String dirTelefono;
}
