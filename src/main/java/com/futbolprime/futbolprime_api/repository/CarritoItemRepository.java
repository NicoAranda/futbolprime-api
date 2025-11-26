package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Carrito;
import com.futbolprime.futbolprime_api.model.CarritoItem;
import com.futbolprime.futbolprime_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    Optional<CarritoItem> findByCarritoAndProducto(Carrito carrito, Producto producto);
    List<CarritoItem> findByCarrito(Carrito carrito);
    void deleteByCarritoAndProducto(Carrito carrito, Producto producto);
    void deleteByCarrito(Carrito carrito);
}
