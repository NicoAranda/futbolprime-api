package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.listaDeseos.ActualizarListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;
import com.futbolprime.futbolprime_api.model.ListaDeseos;
import com.futbolprime.futbolprime_api.repository.ListaDeseosRepository;
import com.futbolprime.futbolprime_api.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaDeseosServiceImpl implements ListaDeseosService {

    private final ListaDeseosRepository listaDeseosRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<ListaDeseosDTO> listarPorUsuario(Long usuarioId) {
        if (usuarioId == null || usuarioId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId es obligatorio y debe ser > 0");
        }
        return listaDeseosRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    @Override
    public ListaDeseosDTO agregar(CrearListaDeseosDTO dto) {
        if (dto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload inválido");
        if (dto.getUsuarioId() == null || dto.getUsuarioId() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId es obligatorio y debe ser > 0");
        if (dto.getProductoId() == null || dto.getProductoId() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productoId es obligatorio y debe ser > 0");

        Long usuarioId = dto.getUsuarioId();
        Long productoId = dto.getProductoId();

        // validar existencia de producto (opcional)
        if (productoRepository != null && !productoRepository.existsById(productoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con id: " + productoId);
        }

        if (listaDeseosRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El producto ya está en la lista de deseos");
        }

        ListaDeseos item = ListaDeseos.builder()
                .usuarioId(usuarioId)
                .productoId(productoId)
                .build();

        try {
            ListaDeseos guardado = listaDeseosRepository.save(item);
            return toDTO(guardado);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No se pudo agregar el item (posible duplicado)");
        }
    }

    @Override
    public void eliminar(Long id) {
        ListaDeseos item = listaDeseosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Elemento no encontrado en la lista de deseos"
                ));

        listaDeseosRepository.delete(item);
    }


    @Override
    public ListaDeseosDTO actualizar(Long id, ActualizarListaDeseosDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id es obligatorio");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload inválido");
        }

        ListaDeseos item = listaDeseosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Elemento no encontrado"));

        if (dto.getProductoId() != null) {
            Long nuevoProductoId = dto.getProductoId();

            if (productoRepository != null && !productoRepository.existsById(nuevoProductoId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado con id: " + nuevoProductoId);
            }

            // Evitar duplicados para el mismo usuario
            if (listaDeseosRepository.existsByUsuarioIdAndProductoId(item.getUsuarioId(), nuevoProductoId)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El producto ya está en la lista de deseos del usuario");
            }

            item.setProductoId(nuevoProductoId);
        }

        ListaDeseos actualizado = listaDeseosRepository.save(item);
        return toDTO(actualizado);
    }

    private ListaDeseosDTO toDTO(ListaDeseos l) {
        ListaDeseosDTO dto = new ListaDeseosDTO();
        dto.setId(l.getId());
        dto.setUsuarioId(l.getUsuarioId());
        dto.setProductoId(l.getProductoId());
        return dto;
    }

}
