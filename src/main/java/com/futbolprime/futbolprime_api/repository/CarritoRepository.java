package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    List<Carrito> findByUsuarioId(UUID usuarioId);

}
