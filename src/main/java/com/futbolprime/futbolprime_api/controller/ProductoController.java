package com.futbolprime.futbolprime_api.controller;

import com.futbolprime.futbolprime_api.dto.producto.ActualizarProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.CrearProductoDTO;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> listarTodos(){
        return productoService.listarTodos();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductoDTO> obtenerPorSku(@PathVariable String sku){
        ProductoDTO dto = productoService.buscarPorSku(sku);

        if (dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("tipo/{tipo}")
    public List<ProductoDTO> listarPorTipo(@PathVariable String tipo){
        return productoService.listarPorTipo(tipo);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CrearProductoDTO request){
        ProductoDTO creado = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{sku}")
    public void eliminarProducto(@PathVariable String sku){
        productoService.eliminarPorSku(sku);
    }

    @PutMapping("/{sku}")
    public ProductoDTO actualizarProducto(
            @PathVariable String sku,
            @RequestBody ActualizarProductoDTO dto
    ) {
        return productoService.actualizarProducto(sku, dto);
    }

}
