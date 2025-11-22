package com.futbolprime.futbolprime_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "listaDeseos", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ListaDeseosItem> items = new ArrayList<>();

}
