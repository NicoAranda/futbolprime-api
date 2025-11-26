package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Pedido;
import com.futbolprime.futbolprime_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioOrderByIdDesc(Usuario usuario);
}
