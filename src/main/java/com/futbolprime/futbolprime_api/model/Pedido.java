package com.futbolprime.futbolprime_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia al usuario (entidad)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 50)
    private String estado; // e.g. "CREADO", "PAGADO", "CANCELADO"

    @Column(nullable = false)
    private Integer subtotal;

    @Column(nullable = false)
    private Integer envio;

    @Column(nullable = false)
    private Integer descuento;

    @Column(nullable = false)
    private Integer total;

    // Dirección de envío simplificada
    @Column(name = "dir_nombre")
    private String dirNombre;

    @Column(name = "dir_linea1")
    private String dirLinea1;

    @Column(name = "dir_linea2")
    private String dirLinea2;

    @Column(name = "dir_ciudad")
    private String dirCiudad;

    @Column(name = "dir_region")
    private String dirRegion;

    @Column(name = "dir_zip")
    private String dirZip;

    @Column(name = "dir_pais")
    private String dirPais;

    @Column(name = "dir_telefono")
    private String dirTelefono;


    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PedidoItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Pago> pagos = new ArrayList<>();

}
