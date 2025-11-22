package com.futbolprime.futbolprime_api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    /**
     * Id de la categoría padre para manejar jerarquía.
     * Puede ser null si es una categoría raíz.
     */
    @Column(name = "padre_id")
    private Long padreId;
}
