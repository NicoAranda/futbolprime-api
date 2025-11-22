package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findBySlugIgnoreCase(String slug);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsBySlugIgnoreCase(String slug);
}
