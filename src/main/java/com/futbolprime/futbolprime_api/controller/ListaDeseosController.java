package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.listaDeseos.ActualizarListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;
import com.futbolprime.futbolprime_api.service.ListaDeseosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lista-deseos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ListaDeseosController {

    private final ListaDeseosService listaDeseosService;

    // GET /api/lista-deseos/{usuarioId}
    @GetMapping("/{usuarioId}")
    public List<ListaDeseosDTO> listarPorUsuario(@PathVariable String usuarioId) {
        return listaDeseosService.listarPorUsuario(usuarioId);
    }

    // POST /api/lista-deseos
    @PostMapping
    public ResponseEntity<ListaDeseosDTO> agregar(@RequestBody CrearListaDeseosDTO request) {
        ListaDeseosDTO dto = listaDeseosService.agregar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // PUT /api/lista-deseos/{id}  (actualiza el producto de un registro de la lista)
    @PutMapping("/{id}")
    public ListaDeseosDTO actualizar(
            @PathVariable Long id,
            @RequestBody ActualizarListaDeseosDTO dto
    ) {
        return listaDeseosService.actualizar(id, dto);
    }

    // DELETE /api/lista-deseos/{usuarioId}/{productoId}
    @DeleteMapping("/{usuarioId}/{productoId}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String usuarioId,
            @PathVariable Long productoId
    ) {
        listaDeseosService.eliminar(usuarioId, productoId);
        return ResponseEntity.noContent().build();
    }
}
