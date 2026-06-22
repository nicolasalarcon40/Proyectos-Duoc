package com.colegio.msestudiantes.service;

import com.colegio.msestudiantes.dto.EstudianteRequestDTO;
import com.colegio.msestudiantes.dto.EstudianteResponseDTO;
import com.colegio.msestudiantes.model.Estudiante;
import com.colegio.msestudiantes.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor 
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    
    private EstudianteResponseDTO mapToDTO(Estudiante e) {
        return new EstudianteResponseDTO(
                e.getId(), e.getRut(), e.getNombre(), e.getApellido(), e.getEdad());
    }

    
    public List<EstudianteResponseDTO> obtenerTodos() {
        return estudianteRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<EstudianteResponseDTO> obtenerPorId(Long id) {
        return estudianteRepository.findById(id).map(this::mapToDTO);
    }

    public List<EstudianteResponseDTO> buscarPorApellido(String apellido) {
        return estudianteRepository.buscarPorApellido(apellido).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    
    public EstudianteResponseDTO guardar(EstudianteRequestDTO dto) {
        if (estudianteRepository.findByRut(dto.getRut()).isPresent()) {
            throw new RuntimeException("Ya existe un estudiante con el RUT: " + dto.getRut());
        }

        Estudiante estudiante = new Estudiante(null, dto.getRut(), dto.getNombre(), dto.getApellido(), dto.getEdad());
        
        return mapToDTO(estudianteRepository.save(estudiante));
    }


    public Optional<EstudianteResponseDTO> actualizar(Long id, EstudianteRequestDTO dto) {
        return estudianteRepository.findById(id).map(existente -> {
            existente.setRut(dto.getRut());
            existente.setNombre(dto.getNombre());
            existente.setApellido(dto.getApellido());
            existente.setEdad(dto.getEdad());
            return mapToDTO(estudianteRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }
}