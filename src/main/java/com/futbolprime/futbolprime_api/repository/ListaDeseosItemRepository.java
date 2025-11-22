package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.ListaDeseos;
import com.futbolprime.futbolprime_api.model.ListaDeseosItem;
import com.futbolprime.futbolprime_api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaDeseosItemRepository extends JpaRepository<ListaDeseosItem, Long> {

    List<ListaDeseosItem> findByListaDeseos(ListaDeseos listaDeseos);

    Optional<ListaDeseosItem> findByListaDeseosAndProducto(ListaDeseos listaDeseos, Producto producto);

    void deleteByListaDeseosAndProducto(ListaDeseos listaDeseos, Producto producto);
}
