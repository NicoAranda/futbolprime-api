package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.listaDeseos.ActualizarListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;
import com.futbolprime.futbolprime_api.model.ListaDeseos;
import com.futbolprime.futbolprime_api.repository.ListaDeseosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaDeseosServiceImpl implements ListaDeseosService {

    private final ListaDeseosRepository listaDeseosRepository;

    @Override
    public List<ListaDeseosDTO> listarPorUsuario(String usuarioId) {
        return listaDeseosRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public ListaDeseosDTO agregar(CrearListaDeseosDTO dto) {
        if (listaDeseosRepository.existsByUsuarioIdAndProductoId(dto.getUsuarioId(), dto.getProductoId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El producto ya está en la lista de deseos");
        }

        ListaDeseos item = ListaDeseos.builder()
                .usuarioId(dto.getUsuarioId())
                .productoId(dto.getProductoId())
                .build();

        ListaDeseos guardado = listaDeseosRepository.save(item);
        return toDTO(guardado);
    }

    @Override
    public void eliminar(String usuarioId, Long productoId) {
        boolean existe = listaDeseosRepository.existsByUsuarioIdAndProductoId(usuarioId, productoId);

        if (!existe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "El producto no está en la lista de deseos");
        }

        listaDeseosRepository.deleteByUsuarioIdAndProductoId(usuarioId, productoId);
    }

    private ListaDeseosDTO toDTO(ListaDeseos l) {
        ListaDeseosDTO dto = new ListaDeseosDTO();
        dto.setId(l.getId());
        dto.setUsuarioId(l.getUsuarioId());
        dto.setProductoId(l.getProductoId());
        dto.setCreadoEn(l.getCreadoEn());
        return dto;
    }

    @Override
    public ListaDeseosDTO actualizar(Long id, ActualizarListaDeseosDTO dto) {
        ListaDeseos item = listaDeseosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Elemento de lista de deseos no encontrado"
                ));

        if (dto.getProductoId() != null) {
            // Evitar duplicados para el mismo usuario
            if (listaDeseosRepository.existsByUsuarioIdAndProductoId(
                    item.getUsuarioId(), dto.getProductoId())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "El producto ya está en la lista de deseos del usuario");
            }
            item.setProductoId(dto.getProductoId());
        }

        ListaDeseos actualizado = listaDeseosRepository.save(item);
        return toDTO(actualizado);
    }

}
