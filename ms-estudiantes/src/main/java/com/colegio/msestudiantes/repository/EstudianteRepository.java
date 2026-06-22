package com.colegio.msestudiantes.repository;

import com.colegio.msestudiantes.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    
    Optional<Estudiante> findByRut(String rut);

    @Query("SELECT e FROM Estudiante e WHERE LOWER(e.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    List<Estudiante> buscarPorApellido(@Param("apellido") String apellido);
}