package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.ListaDeseos;
import com.futbolprime.futbolprime_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaDeseosRepository extends JpaRepository<ListaDeseos, Long> {

    Optional<ListaDeseos> findByUsuario(Usuario usuario);

}
