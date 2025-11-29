package com.futbolprime.futbolprime_api.dto.pago;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "PagoDTO",
        description = "Representa la información del pago asociado a un pedido."
)
public class PagoDTO {

    @Schema(
            description = "ID único del pago",
            example = "102"
    )
    private Long id;

    @Schema(
            description = "Proveedor o plataforma de pago utilizada (WebPay, PayPal, MercadoPago, etc.)",
            example = "WebPay"
    )
    private String proveedor;

    @Schema(
            description = "Método de pago seleccionado por el usuario (Crédito, Débito, Transferencia)",
            example = "Débito"
    )
    private String metodo;

    @Schema(
            description = "Monto total pagado por el usuario (en CLP o moneda definida)",
            example = "25990"
    )
    private Integer monto;

    @Schema(
            description = "Estado del pago (PENDIENTE, APROBADO, RECHAZADO)",
            example = "APROBADO"
    )
    private String estado;

    @Schema(
            description = "Referencia o código de transacción entregado por el proveedor",
            example = "TXP-998123512"
    )
    private String transaccionRef;
}
