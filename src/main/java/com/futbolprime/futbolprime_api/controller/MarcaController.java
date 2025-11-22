package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.marca.ActualizarMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.CrearMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.MarcaDTO;
import com.futbolprime.futbolprime_api.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarcaController {
    private final MarcaService marcaService;

    @GetMapping
    public List<MarcaDTO> listarTodas() {
        return marcaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> obtenerPorId(@PathVariable Long id) {
        MarcaDTO dto = marcaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // Alternativa: GET /api/marcas/slug/{slug}
    @GetMapping("/slug/{slug}")
    public ResponseEntity<MarcaDTO> obtenerPorSlug(@PathVariable String slug) {
        MarcaDTO dto = marcaService.buscarPorSlug(slug);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> crearMarca(@RequestBody CrearMarcaDTO request) {
        MarcaDTO creado = marcaService.crearMarca(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public void eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarPorId(id);
    }

    @PutMapping("/{id}")
    public MarcaDTO actualizarMarca(
            @PathVariable Long id,
            @RequestBody ActualizarMarcaDTO dto
    ) {
        return marcaService.actualizarMarca(id, dto);
    }
}
