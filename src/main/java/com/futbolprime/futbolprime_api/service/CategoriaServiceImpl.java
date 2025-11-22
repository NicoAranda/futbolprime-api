package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.categoria.ActualizarCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CrearCategoriaDTO;
import com.futbolprime.futbolprime_api.dto.categoria.CategoriaDTO;
import com.futbolprime.futbolprime_api.model.Categoria;
import com.futbolprime.futbolprime_api.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada"
                ));
        return toDTO(categoria);
    }

    @Override
    public CategoriaDTO buscarPorSlug(String slug) {
        Categoria categoria = categoriaRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada"
                ));
        return toDTO(categoria);
    }

    @Override
    public List<CategoriaDTO> listarPorPadre(Long padreId) {
        return categoriaRepository.findByPadreId(padreId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public CategoriaDTO crearCategoria(CrearCategoriaDTO dto) {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }

        if (dto.getSlug() == null || dto.getSlug().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El slug es obligatorio");
        }

        String slugNormalizado = dto.getSlug().trim().toLowerCase();

        if (categoriaRepository.existsBySlug(slugNormalizado)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoría con ese slug");
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre().trim())
                .slug(slugNormalizado)
                .padreId(dto.getPadreId())
                .build();

        Categoria guardada = categoriaRepository.save(categoria);
        return toDTO(guardada);
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, ActualizarCategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada"
                ));

        if (dto.getNombre() != null) {
            categoria.setNombre(dto.getNombre().trim());
        }

        if (dto.getSlug() != null) {
            String slugNormalizado = dto.getSlug().trim().toLowerCase();

            // Validar que el nuevo slug no esté usando otra categoría
            if (!slugNormalizado.equals(categoria.getSlug())
                    && categoriaRepository.existsBySlug(slugNormalizado)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoría con ese slug");
            }

            categoria.setSlug(slugNormalizado);
        }

        if (dto.getPadreId() != null) {
            categoria.setPadreId(dto.getPadreId());
        }

        Categoria actualizada = categoriaRepository.save(categoria);
        return toDTO(actualizada);
    }

    @Override
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoría no encontrada"
                ));

        categoriaRepository.delete(categoria);
    }

    private CategoriaDTO toDTO(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setSlug(c.getSlug());
        dto.setPadreId(c.getPadreId());
        return dto;
    }
}
