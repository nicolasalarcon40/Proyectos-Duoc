package com.colegio.msfinanzas.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PagoResponseDTO {
    private Long id;
    private Double monto;
    private LocalDate fechaVencimiento;
    private String estado;
    private String rutEstudiante;
}