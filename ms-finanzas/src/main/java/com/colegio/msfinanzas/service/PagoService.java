package com.colegio.msfinanzas.service;

import com.colegio.msfinanzas.client.EstudianteClient;
import com.colegio.msfinanzas.dto.PagoRequestDTO;
import com.colegio.msfinanzas.dto.PagoResponseDTO;
import com.colegio.msfinanzas.exception.ResourceNotFoundException;
import com.colegio.msfinanzas.model.Pago;
import com.colegio.msfinanzas.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository repository;
    private final EstudianteClient estudianteClient;

    public PagoResponseDTO crear(PagoRequestDTO request) {
        estudianteClient.obtenerPorRut(request.getRutEstudiante());

        Pago entidad = new Pago();
        entidad.setMonto(request.getMonto());
        entidad.setFechaVencimiento(request.getFechaVencimiento());
        entidad.setEstado(request.getEstado());
        entidad.setRutEstudiante(request.getRutEstudiante());

        Pago guardado = repository.save(entidad);
        return mapearDTO(guardado);
    }

    public List<PagoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::mapearDTO)
                .collect(Collectors.toList());
    }

    public PagoResponseDTO actualizar(Long id, PagoRequestDTO request) {
        Pago entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID de pago no encontrado: " + id));

        estudianteClient.obtenerPorRut(request.getRutEstudiante());

        entidad.setMonto(request.getMonto());
        entidad.setFechaVencimiento(request.getFechaVencimiento());
        entidad.setEstado(request.getEstado());
        entidad.setRutEstudiante(request.getRutEstudiante());

        Pago actualizado = repository.save(entidad);
        return mapearDTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ID de pago no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private PagoResponseDTO mapearDTO(Pago entidad) {
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(entidad.getId());
        dto.setMonto(entidad.getMonto());
        dto.setFechaVencimiento(entidad.getFechaVencimiento());
        dto.setEstado(entidad.getEstado());
        dto.setRutEstudiante(entidad.getRutEstudiante());
        return dto;
    }
}