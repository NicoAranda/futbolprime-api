package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.listaDeseos.CrearListaDeseosItemDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosDTO;
import com.futbolprime.futbolprime_api.dto.listaDeseos.ListaDeseosItemDTO;
import com.futbolprime.futbolprime_api.dto.producto.ProductoDTO;
import com.futbolprime.futbolprime_api.model.ListaDeseos;
import com.futbolprime.futbolprime_api.model.ListaDeseosItem;
import com.futbolprime.futbolprime_api.model.Producto;
import com.futbolprime.futbolprime_api.model.Usuario;
import com.futbolprime.futbolprime_api.repository.ListaDeseosItemRepository;
import com.futbolprime.futbolprime_api.repository.ListaDeseosRepository;
import com.futbolprime.futbolprime_api.repository.ProductoRepository;
import com.futbolprime.futbolprime_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListaDeseosServiceImpl implements com.futbolprime.futbolprime_api.service.ListaDeseosService {

    private final ListaDeseosRepository listaDeseosRepository;
    private final ListaDeseosItemRepository itemRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Override
    public ListaDeseosDTO obtenerPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByUsuario(usuario)
                .orElse(ListaDeseos.builder().usuario(usuario).build());

        List<ListaDeseosItemDTO> items = itemRepository.findByListaDeseos(lista)
                .stream().map(this::toItemDTO).collect(Collectors.toList());

        ListaDeseosDTO dto = new ListaDeseosDTO();
        dto.setId(lista.getId());
        dto.setUsuarioId(usuarioId);
        dto.setItems(items);

        return dto;
    }

    @Override
    public ListaDeseosItemDTO agregarProducto(CrearListaDeseosItemDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByUsuario(usuario)
                .orElseGet(() -> listaDeseosRepository.save(ListaDeseos.builder().usuario(usuario).build()));

        // Evitar duplicados
        if (itemRepository.findByListaDeseosAndProducto(lista, producto).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Producto ya existe en la lista de deseos");
        }

        ListaDeseosItem item = ListaDeseosItem.builder()
                .listaDeseos(lista)
                .producto(producto)
                .build();

        itemRepository.save(item);

        return toItemDTO(item);
    }

    @Override
    public void eliminarProducto(Long usuarioId, Long productoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de deseos no encontrada"));

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        itemRepository.deleteByListaDeseosAndProducto(lista, producto);
    }

    @Override
    public List<ListaDeseosItemDTO> listarItems(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        ListaDeseos lista = listaDeseosRepository.findByUsuario(usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de deseos no encontrada"));

        return itemRepository.findByListaDeseos(lista).stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
    }

    private ListaDeseosItemDTO toItemDTO(ListaDeseosItem item) {
        ListaDeseosItemDTO dto = new ListaDeseosItemDTO();
        dto.setId(item.getId());

        Producto producto = item.getProducto();

        // Mapear manualmente para evitar proxy
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setSku(producto.getSku());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setOferta(producto.getOferta());
        productoDTO.setTipo(producto.getTipo());
        productoDTO.setTalla(producto.getTalla());
        productoDTO.setColor(producto.getColor());

        if (producto.getMarca() != null) {
            productoDTO.setMarcaId(producto.getMarca().getId());
            productoDTO.setMarcaNombre(producto.getMarca().getNombre());
        }

        dto.setProducto(productoDTO);
        return dto;
    }

}
