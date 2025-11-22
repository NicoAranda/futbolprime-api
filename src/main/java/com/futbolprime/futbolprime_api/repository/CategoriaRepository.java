package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findBySlug(String slug);

    List<Categoria> findByPadreId(Long padreId);

    boolean existsBySlug(String slug);
}
