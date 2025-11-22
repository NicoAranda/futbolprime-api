package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosItemDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosItemDTO;
import com.futbolprime.futbolprime_api.service.ListaDeseosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/lista-deseos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ListaDeseosController {
    private final ListaDeseosService listaDeseosService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ListaDeseosDTO> obtenerLista(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(listaDeseosService.obtenerPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<ListaDeseosItemDTO> agregarProducto(@RequestBody CrearListaDeseosItemDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(listaDeseosService.agregarProducto(dto));
    }

    @DeleteMapping("/usuario/{usuarioId}/producto/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long usuarioId,
                                                 @PathVariable Long productoId) {
        listaDeseosService.eliminarProducto(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<List<ListaDeseosItemDTO>> listarItems(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(listaDeseosService.listarItems(usuarioId));
    }
}
