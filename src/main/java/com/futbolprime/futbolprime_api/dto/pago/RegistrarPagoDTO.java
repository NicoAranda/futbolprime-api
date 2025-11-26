package com.futbolprime.futbolprime_api.dto.pago;

import lombok.Data;

@Data
public class RegistrarPagoDTO {
    private String proveedor;
    private String metodo;
    private Integer monto;
    private String transaccionRef;
    private String estado;
}
