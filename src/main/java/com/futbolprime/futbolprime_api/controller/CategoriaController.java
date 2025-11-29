package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.categoria.ActualizarCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CrearCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CategoriaDTO;
import com.futbolprime.futbolprime_api.service.CategoriaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Categorías", description = "Operaciones relacionadas con la gestión de categorías de productos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    // =========================================================================
    // Listar todas las categorías
    // =========================================================================
    @Operation(
            summary = "Listar todas las categorías",
            description = "Devuelve la lista completa de categorías registradas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista obtenida exitosamente",
                            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
                    )
            }
    )
    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodas();
    }

    // =========================================================================
    // Obtener categoría por ID
    // =========================================================================
    @Operation(
            summary = "Obtener categoría por ID",
            description = "Retorna la información de una categoría según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría encontrada",
                            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    // =========================================================================
    // Obtener categoría por slug
    // =========================================================================
    @Operation(
            summary = "Obtener categoría por slug",
            description = "Busca una categoría usando su slug único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría encontrada",
                            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoriaDTO> obtenerPorSlug(@PathVariable String slug) {
        return ResponseEntity.ok(categoriaService.buscarPorSlug(slug));
    }

    // =========================================================================
    // Listar categorías por ID del padre
    // =========================================================================
    @Operation(
            summary = "Listar categorías por categoría padre",
            description = "Devuelve todas las subcategorías cuyo padre tiene el ID especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Subcategorías obtenidas exitosamente",
                            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
                    )
            }
    )
    @GetMapping("/padre/{padreId}")
    public List<CategoriaDTO> listarPorPadre(@PathVariable Long padreId) {
        return categoriaService.listarPorPadre(padreId);
    }

    // =========================================================================
    // Crear categoría
    // =========================================================================
    @Operation(
            summary = "Crear categoría",
            description = "Crea una nueva categoría en el sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Categoría creada exitosamente",
                            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos enviados"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CrearCategoriaDTO request) {
        CategoriaDTO creada = categoriaService.crearCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // =========================================================================
    // Actualizar categoría
    // =========================================================================
    @Operation(
            summary = "Actualizar categoría",
            description = "Modifica la información de una categoría existente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría actualizada exitosamente",
                            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    @PutMapping("/{id}")
    public CategoriaDTO actualizarCategoria(
            @PathVariable Long id,
            @RequestBody ActualizarCategoriaDTO dto
    ) {
        return categoriaService.actualizarCategoria(id, dto);
    }

    // =========================================================================
    // Eliminar categoría
    // =========================================================================
    @Operation(
            summary = "Eliminar categoría",
            description = "Elimina una categoría del sistema según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Categoría eliminada exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
