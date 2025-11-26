package com.futbolprime.futbolprime_api.dto.pago;

import lombok.Data;

@Data
public class PagoDTO {

    private Long id;
    private String proveedor;
    private String metodo;
    private Integer monto;
    private String estado;
    private String transaccionRef;
}
