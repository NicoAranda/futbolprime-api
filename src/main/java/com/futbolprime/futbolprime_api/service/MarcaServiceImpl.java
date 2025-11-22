package com.futbolprime.futbolprime_api.service;

import com.futbolprime.futbolprime_api.dto.marca.ActualizarMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.CrearMarcaDTO;
import com.futbolprime.futbolprime_api.dto.marca.MarcaDTO;
import com.futbolprime.futbolprime_api.model.Marca;
import com.futbolprime.futbolprime_api.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    @Override
    public List<MarcaDTO> listarTodas() {
        return marcaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public MarcaDTO buscarPorId(Long id) {
        Marca m = marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca no encontada"
                ));
        return toDTO(m);
    }

    @Override
    public MarcaDTO buscarPorSlug(String slug) {
        Marca m = marcaRepository.findBySlugIgnoreCase(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca no encontada"
                ));
        return toDTO(m);
    }

    @Override
    public MarcaDTO crearMarca(CrearMarcaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }

        if (dto.getSlug() == null || dto.getSlug().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El slug es obligatorio");
        }

        if (marcaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una marca con ese nombre");
        }

        if (marcaRepository.existsBySlugIgnoreCase(dto.getSlug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una marca con ese slug");
        }


        Marca marca = Marca.builder()
                .nombre(dto.getNombre().trim())
                .slug(dto.getSlug().trim().toLowerCase())
                .build();

        Marca guardada = marcaRepository.save(marca);
        return toDTO(guardada);
    }

    @Override
    public void eliminarPorId(Long id) {
        Marca m = marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca no encontada"
                ));
        marcaRepository.delete(m);
    }

    @Override
    public MarcaDTO actualizarMarca(Long id, ActualizarMarcaDTO dto) {
        Marca m = marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca no encontada"
                ));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            String nuevoNombre = dto.getNombre().trim();
            if (!m.getNombre().equalsIgnoreCase(nuevoNombre)
                    && marcaRepository.existsByNombreIgnoreCase(nuevoNombre)) {
                throw new RuntimeException("Ya existe otra marca con ese nombre");
            }
            m.setNombre(nuevoNombre);
        }

        if (dto.getSlug() != null && !dto.getSlug().isBlank()) {
            String nuevoSlug = dto.getSlug().trim().toLowerCase();
            if (!m.getSlug().equalsIgnoreCase(nuevoSlug)
                    && marcaRepository.existsBySlugIgnoreCase(nuevoSlug)) {
                throw new RuntimeException("Ya existe otra marca con ese slug");
            }
            m.setSlug(nuevoSlug);
        }

        Marca actualizado = marcaRepository.save(m);
        return toDTO(actualizado);
    }

    private MarcaDTO toDTO(Marca m) {
        MarcaDTO dto = new MarcaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setSlug(m.getSlug());
        return dto;
    }
}
