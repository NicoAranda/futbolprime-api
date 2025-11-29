package com.futbolprime.futbolprime_api.dto.pago;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "RegistrarPagoDTO",
        description = "DTO utilizado para registrar un pago asociado a un pedido."
)
public class RegistrarPagoDTO {

    @Schema(
            description = "Proveedor o plataforma de pago utilizada (WebPay, PayPal, MercadoPago, etc.)",
            example = "WebPay",
            required = true
    )
    private String proveedor;

    @Schema(
            description = "Método de pago seleccionado (Crédito, Débito, Transferencia)",
            example = "Crédito",
            required = true
    )
    private String metodo;

    @Schema(
            description = "Monto total del pago realizado",
            example = "25990",
            required = true
    )
    private Integer monto;

    @Schema(
            description = "Código o referencia de la transacción entregado por el proveedor de pago",
            example = "TXP-451236789",
            required = true
    )
    private String transaccionRef;

    @Schema(
            description = "Estado del pago (PENDIENTE, APROBADO, RECHAZADO)",
            example = "APROBADO",
            required = true
    )
    private String estado;
}
