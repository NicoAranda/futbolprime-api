package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {

    List<ListaDeseos> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    void deleteByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    Optional<ListaDeseos> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
}
