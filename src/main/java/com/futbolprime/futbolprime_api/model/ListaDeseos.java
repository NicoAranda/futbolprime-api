package com.futbolprime.futbolprime_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lista_deseos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaDeseos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false, length = 36)
    private Long usuarioId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

}
