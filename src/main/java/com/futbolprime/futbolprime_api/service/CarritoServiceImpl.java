package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.carrito.*;
import com.futbolprime.futbolprime_api.model.Carrito;
import com.futbolprime.futbolprime_api.repository.CarritoRepository;
import com.futbolprime.futbolprime_api.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;

    @Override
    public List<CarritoDTO> listarTodos() {
        return carritoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<CarritoDTO> listarPorUsuario(UUID usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public CarritoDTO buscarPorId(Long id) {
        return carritoRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public CarritoDTO crearCarrito(CrearCarritoDTO dto) {
        Carrito carrito = Carrito.builder()
                .usuarioId(dto.getUsuarioId())
                .estado(dto.getEstado())
                .build();

        carritoRepository.save(carrito);
        return mapToDTO(carrito);
    }

    @Override
    public CarritoDTO actualizarCarrito(Long id, ActualizarCarritoDTO dto) {
        Carrito carrito = carritoRepository.findById(id)
                .orElse(null);

        if (carrito == null) {
            return null;
        }

        carrito.setEstado(dto.getEstado());
        carritoRepository.save(carrito);

        return mapToDTO(carrito);
    }

    @Override
    public void eliminarCarrito(Long id) {
        carritoRepository.deleteById(id);
    }

    private CarritoDTO mapToDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuarioId());
        dto.setEstado(carrito.getEstado());
        dto.setCreadoEn(carrito.getCreadoEn());
        dto.setActualizadoEn(carrito.getActualizadoEn());
        return dto;
    }
}
