package com.colegio.msasignaturas.service;

import com.colegio.msasignaturas.dto.AsignaturaRequestDTO;
import com.colegio.msasignaturas.dto.AsignaturaResponseDTO;
import com.colegio.msasignaturas.exception.ResourceNotFoundException;
import com.colegio.msasignaturas.model.Asignatura;
import com.colegio.msasignaturas.repository.AsignaturaRepository;
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
class AsignaturaServiceTest {

    @Mock
    private AsignaturaRepository repository;

    @InjectMocks
    private AsignaturaService asignaturaService;

    private Asignatura asignatura;
    private AsignaturaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        asignatura = new Asignatura(1L, "Matematicas", "Ciencias Exactas");

        requestDTO = new AsignaturaRequestDTO();
        requestDTO.setNombre("Matematicas");
        requestDTO.setDepartamento("Ciencias Exactas");
    }

    
    @Test
    void testCrearAsignatura_debeRetornarResponseDTO() {
        
        when(repository.save(any(Asignatura.class))).thenReturn(asignatura);

        
        AsignaturaResponseDTO resultado = asignaturaService.crearAsignatura(requestDTO);

        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Matematicas", resultado.getNombre());
        assertEquals("Ciencias Exactas", resultado.getDepartamento());
        verify(repository, times(1)).save(any(Asignatura.class));
    }

    
    @Test
    void testListarTodas_debeRetornarListaDeAsignaturas() {
        
        Asignatura asignatura2 = new Asignatura(2L, "Historia", "Humanidades");
        when(repository.findAll()).thenReturn(List.of(asignatura, asignatura2));

        
        List<AsignaturaResponseDTO> resultado = asignaturaService.listarTodas();

        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Matematicas", resultado.get(0).getNombre());
        assertEquals("Historia", resultado.get(1).getNombre());
        verify(repository, times(1)).findAll();
    }


    @Test
    void testBuscarPorId_cuandoNoExiste_debeLanzarExcepcion() {
        
        when(repository.findById(99L)).thenReturn(Optional.empty());

        
        ResourceNotFoundException ex = assertThrows(
            ResourceNotFoundException.class,
            () -> asignaturaService.buscarPorId(99L)
        );

        assertEquals("Asignatura no encontrada con ID: 99", ex.getMessage());
        verify(repository, times(1)).findById(99L);
    }
}