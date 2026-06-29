package com.colegio.msfinanzas.service;

import com.colegio.msfinanzas.client.EstudianteClient;
import com.colegio.msfinanzas.dto.PagoRequestDTO;
import com.colegio.msfinanzas.dto.PagoResponseDTO;
import com.colegio.msfinanzas.exception.ResourceNotFoundException;
import com.colegio.msfinanzas.model.Pago;
import com.colegio.msfinanzas.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private EstudianteClient estudianteClient;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private PagoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        pago = new Pago(1L, 50000.0, LocalDate.of(2026, 7, 1), "PENDIENTE", "12345678-9");

        requestDTO = new PagoRequestDTO();
        requestDTO.setMonto(50000.0);
        requestDTO.setFechaVencimiento(LocalDate.of(2026, 7, 1));
        requestDTO.setEstado("PENDIENTE");
        requestDTO.setRutEstudiante("12345678-9");
    }

    
    @Test
    void testCrear_debeRetornarPagoCreado() {
        
        when(estudianteClient.obtenerPorRut("12345678-9")).thenReturn("estudiante_mock");
        when(repository.save(any(Pago.class))).thenReturn(pago);

        
        PagoResponseDTO resultado = pagoService.crear(requestDTO);

       
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50000.0, resultado.getMonto());
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals("12345678-9", resultado.getRutEstudiante());
        verify(repository, times(1)).save(any(Pago.class));
        verify(estudianteClient, times(1)).obtenerPorRut("12345678-9");
    }

    
    @Test
    void testListarTodos_debeRetornarLista() {
        
        Pago pago2 = new Pago(2L, 30000.0, LocalDate.of(2026, 8, 1), "PAGADO", "98765432-1");
        when(repository.findAll()).thenReturn(List.of(pago, pago2));

        
        List<PagoResponseDTO> resultado = pagoService.listarTodos();

        
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
        assertEquals("PAGADO", resultado.get(1).getEstado());
        verify(repository, times(1)).findAll();
    }

   
    @Test
    void testEliminar_cuandoNoExiste_debeLanzarExcepcion() {
        
        when(repository.existsById(99L)).thenReturn(false);

       
        ResourceNotFoundException ex = assertThrows(
            ResourceNotFoundException.class,
            () -> pagoService.eliminar(99L)
        );

        assertEquals("ID de pago no encontrado: 99", ex.getMessage());
        verify(repository, never()).deleteById(any());
    }
}