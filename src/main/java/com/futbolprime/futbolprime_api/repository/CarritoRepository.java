package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Carrito;
import com.futbolprime.futbolprime_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    Optional<Carrito> findByUsuarioAndEstado(Usuario usuarioId, String estado);

}
