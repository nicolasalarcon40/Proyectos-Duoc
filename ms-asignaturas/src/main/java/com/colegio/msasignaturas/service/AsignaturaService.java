package com.colegio.msasignaturas.service;

import com.colegio.msasignaturas.dto.AsignaturaRequestDTO;
import com.colegio.msasignaturas.dto.AsignaturaResponseDTO;
import com.colegio.msasignaturas.exception.ResourceNotFoundException;
import com.colegio.msasignaturas.model.Asignatura;
import com.colegio.msasignaturas.repository.AsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsignaturaService {

    private final AsignaturaRepository repository;

    public AsignaturaResponseDTO crearAsignatura(AsignaturaRequestDTO request) {
        Asignatura nuevaAsignatura = new Asignatura();
        nuevaAsignatura.setNombre(request.getNombre());
        nuevaAsignatura.setDepartamento(request.getDepartamento());

        Asignatura asignaturaGuardada = repository.save(nuevaAsignatura);

        AsignaturaResponseDTO response = new AsignaturaResponseDTO();
        response.setId(asignaturaGuardada.getId());
        response.setNombre(asignaturaGuardada.getNombre());
        response.setDepartamento(asignaturaGuardada.getDepartamento());

        return response;
    }

    public List<AsignaturaResponseDTO> listarTodas() {
        return repository.findAll().stream().map(asignatura -> {
            AsignaturaResponseDTO dto = new AsignaturaResponseDTO();
            dto.setId(asignatura.getId());
            dto.setNombre(asignatura.getNombre());
            dto.setDepartamento(asignatura.getDepartamento());
            return dto;
        }).collect(Collectors.toList());
    }

    public AsignaturaResponseDTO actualizarAsignatura(Long id, AsignaturaRequestDTO request) {
        Asignatura asignaturaExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con ID: " + id));

        asignaturaExistente.setNombre(request.getNombre());
        asignaturaExistente.setDepartamento(request.getDepartamento());

        Asignatura asignaturaActualizada = repository.save(asignaturaExistente);

        AsignaturaResponseDTO response = new AsignaturaResponseDTO();
        response.setId(asignaturaActualizada.getId());
        response.setNombre(asignaturaActualizada.getNombre());
        response.setDepartamento(asignaturaActualizada.getDepartamento());

        return response;
    }

    public AsignaturaResponseDTO buscarPorId(Long id) {
        
        Asignatura asignatura = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignatura no encontrada con ID: " + id));

       
        AsignaturaResponseDTO response = new AsignaturaResponseDTO();
        response.setId(asignatura.getId());
        response.setNombre(asignatura.getNombre());
        response.setDepartamento(asignatura.getDepartamento());

        return response;
    }
    public void eliminarAsignatura(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Asignatura no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
}