package com.colegio.msestudiantes.service;

import com.colegio.msestudiantes.dto.EstudianteRequestDTO;
import com.colegio.msestudiantes.dto.EstudianteResponseDTO;
import com.colegio.msestudiantes.model.Estudiante;
import com.colegio.msestudiantes.repository.EstudianteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    private Estudiante estudiante;
    private EstudianteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        estudiante = new Estudiante(1L, "12345678-9", "Juan", "Perez", 15);

        requestDTO = new EstudianteRequestDTO();
        requestDTO.setRut("12345678-9");
        requestDTO.setNombre("Juan");
        requestDTO.setApellido("Perez");
        requestDTO.setEdad(15);
    }

   
    @Test
    void testGuardar_debeRetornarEstudianteGuardado() {
        
        when(estudianteRepository.findByRut("12345678-9")).thenReturn(Optional.empty());
        when(estudianteRepository.save(any(Estudiante.class))).thenReturn(estudiante);

        
        EstudianteResponseDTO resultado = estudianteService.guardar(requestDTO);

        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals(15, resultado.getEdad());
        verify(estudianteRepository, times(1)).save(any(Estudiante.class));
    }

    
    @Test
    void testGuardar_conRutDuplicado_debeLanzarExcepcion() {
        
        when(estudianteRepository.findByRut("12345678-9")).thenReturn(Optional.of(estudiante));

        
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> estudianteService.guardar(requestDTO)
        );

        assertEquals("Ya existe un estudiante con el RUT: 12345678-9", ex.getMessage());
        verify(estudianteRepository, never()).save(any());
    }

    
    @Test
    void testObtenerTodos_debeRetornarLista() {
        
        Estudiante estudiante2 = new Estudiante(2L, "98765432-1", "Maria", "Lopez", 14);
        when(estudianteRepository.findAll()).thenReturn(List.of(estudiante, estudiante2));

        
        List<EstudianteResponseDTO> resultado = estudianteService.obtenerTodos();

        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Maria", resultado.get(1).getNombre());
        verify(estudianteRepository, times(1)).findAll();
    }
}