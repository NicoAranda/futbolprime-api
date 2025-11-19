package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findBySku(String sku);

    List<Producto> findByTipoIgnoreCase(String tipo);
}
