package com.colegio.msasignaturas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asignaturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;       
    private String departamento; 
}