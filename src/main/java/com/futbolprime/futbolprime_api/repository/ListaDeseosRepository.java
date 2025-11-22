package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.ListaDeseos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {

    List<ListaDeseos> findByUsuarioId(String usuarioId);

    boolean existsByUsuarioIdAndProductoId(String usuarioId, Long productoId);

    void deleteByUsuarioIdAndProductoId(String usuarioId, Long productoId);
}
