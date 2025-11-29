package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.marca.ActualizarMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.CrearMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.MarcaDTO;
import com.futbolprime.futbolprime_api.service.MarcaService;

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
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Marcas", description = "Operaciones relacionadas con la gestión de marcas")
public class MarcaController {

    private final MarcaService marcaService;

    // ========================================================================
    // Listar todas las marcas
    // ========================================================================
    @Operation(
            summary = "Listar todas las marcas",
            description = "Devuelve una lista con todas las marcas registradas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de marcas obtenida exitosamente",
                            content = @Content(schema = @Schema(implementation = MarcaDTO.class))
                    )
            }
    )
    @GetMapping
    public List<MarcaDTO> listarTodas() {
        return marcaService.listarTodas();
    }

    // ========================================================================
    // Obtener marca por ID
    // ========================================================================
    @Operation(
            summary = "Obtener marca por ID",
            description = "Devuelve los datos de una marca según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Marca encontrada",
                            content = @Content(schema = @Schema(implementation = MarcaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Marca no encontrada"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> obtenerPorId(@PathVariable Long id) {
        MarcaDTO dto = marcaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // ========================================================================
    // Obtener marca por slug
    // ========================================================================
    @Operation(
            summary = "Obtener marca por slug",
            description = "Busca una marca utilizando su slug único",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Marca encontrada",
                            content = @Content(schema = @Schema(implementation = MarcaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Marca no encontrada"
                    )
            }
    )
    @GetMapping("/slug/{slug}")
    public ResponseEntity<MarcaDTO> obtenerPorSlug(@PathVariable String slug) {
        MarcaDTO dto = marcaService.buscarPorSlug(slug);
        return ResponseEntity.ok(dto);
    }

    // ========================================================================
    // Crear nueva marca
    // ========================================================================
    @Operation(
            summary = "Crear nueva marca",
            description = "Registra una nueva marca dentro del sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Marca creada exitosamente",
                            content = @Content(schema = @Schema(implementation = MarcaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos enviados inválidos"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<MarcaDTO> crearMarca(@RequestBody CrearMarcaDTO request) {
        MarcaDTO creado = marcaService.crearMarca(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ========================================================================
    // Eliminar marca
    // ========================================================================
    @Operation(
            summary = "Eliminar marca",
            description = "Elimina una marca del sistema por su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Marca eliminada exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Marca no encontrada"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // ========================================================================
    // Actualizar marca
    // ========================================================================
    @Operation(
            summary = "Actualizar una marca",
            description = "Actualiza la información de una marca existente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Marca actualizada exitosamente",
                            content = @Content(schema = @Schema(implementation = MarcaDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Marca no encontrada"
                    )
            }
    )
    @PutMapping("/{id}")
    public MarcaDTO actualizarMarca(
            @PathVariable Long id,
            @RequestBody ActualizarMarcaDTO dto
    ) {
        return marcaService.actualizarMarca(id, dto);
    }
}
