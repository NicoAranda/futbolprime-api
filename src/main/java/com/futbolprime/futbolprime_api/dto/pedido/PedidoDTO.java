package com.futbolprime.futbolprime_api.dto.pedido;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "PedidoDTO",
        description = "Representa un pedido completo realizado por un usuario, incluyendo totales, dirección e ítems."
)
public class PedidoDTO {

    @Schema(
            description = "ID único del pedido",
            example = "1204"
    )
    private Long id;

    @Schema(
            description = "ID del usuario que realizó el pedido",
            example = "12"
    )
    private Long usuarioId;

    @Schema(
            description = "Estado actual del pedido: PENDIENTE, PAGADO, ENVIADO, COMPLETADO, CANCELADO",
            example = "PAGADO"
    )
    private String estado;

    @Schema(
            description = "Subtotal del pedido antes de envío y descuentos",
            example = "45980"
    )
    private Integer subtotal;

    @Schema(
            description = "Costo de envío aplicado",
            example = "3990"
    )
    private Integer envio;

    @Schema(
            description = "Monto de descuento aplicado",
            example = "2000"
    )
    private Integer descuento;

    @Schema(
            description = "Total final del pedido después de aplicar envío y descuentos",
            example = "47970"
    )
    private Integer total;


    @Schema(
            description = "Nombre completo del receptor del pedido",
            example = "Fernanda Soto"
    )
    private String dirNombre;

    @Schema(
            description = "Línea principal de la dirección (calle y número)",
            example = "Av. Las Torres 1234"
    )
    private String dirLinea1;

    @Schema(
            description = "Línea adicional de dirección (departamento, oficina). Puede ser null.",
            example = "Depto 504",
            nullable = true
    )
    private String dirLinea2;

    @Schema(
            description = "Ciudad del envío",
            example = "Santiago"
    )
    private String dirCiudad;

    @Schema(
            description = "Región o estado",
            example = "Región Metropolitana"
    )
    private String dirRegion;

    @Schema(
            description = "Código postal (ZIP)",
            example = "9020000"
    )
    private String dirZip;

    @Schema(
            description = "País del envío",
            example = "Chile"
    )
    private String dirPais;

    @Schema(
            description = "Número telefónico del destinatario",
            example = "+56945678912"
    )
    private String dirTelefono;


    @Schema(
            description = "Lista de productos que forman parte del pedido",
            implementation = PedidoItemDTO.class
    )
    private List<PedidoItemDTO> items;
}
