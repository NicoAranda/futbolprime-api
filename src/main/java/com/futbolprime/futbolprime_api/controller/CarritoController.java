package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.carrito.ActualizarCarritoDTO;
import com.futbolprime.futbolprime_api.dto.carrito.CrearCarritoDTO;
import com.futbolprime.futbolprime_api.dto.carrito.CarritoDTO;
import com.futbolprime.futbolprime_api.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping
    public List<CarritoDTO> listarTodos(){
        return carritoService.listarTodos();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<CarritoDTO> listarPorUsuario(@PathVariable Long usuarioId){
        return carritoService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarritoDTO> obtenerPorId(@PathVariable Long id){
        CarritoDTO dto = carritoService.buscarPorId(id);

        if (dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CarritoDTO> crearCarrito(@RequestBody CrearCarritoDTO request){
        CarritoDTO creado = carritoService.crearCarrito(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public CarritoDTO actualizarCarrito(
            @PathVariable Long id,
            @RequestBody ActualizarCarritoDTO dto
    ) {
        return carritoService.actualizarCarrito(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarCarrito(@PathVariable Long id){
        carritoService.eliminarCarrito(id);
    }

}
