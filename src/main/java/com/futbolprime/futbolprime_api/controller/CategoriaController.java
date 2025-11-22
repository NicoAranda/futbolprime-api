package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.categoria.ActualizarCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CrearCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CategoriaDTO;
import com.futbolprime.futbolprime_api.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoriaDTO> obtenerPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(categoriaService.buscarPorSlug(slug));
    }

    @GetMapping("/padre/{padreId}")
    public List<CategoriaDTO> listarPorPadre(@PathVariable Long padreId) {
        return categoriaService.listarPorPadre(padreId);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CrearCategoriaDTO request) {
        CategoriaDTO creada = categoriaService.crearCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizarCategoria(
            @PathVariable Long id,
            @RequestBody ActualizarCategoriaDTO dto
    ) {
        return categoriaService.actualizarCategoria(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
