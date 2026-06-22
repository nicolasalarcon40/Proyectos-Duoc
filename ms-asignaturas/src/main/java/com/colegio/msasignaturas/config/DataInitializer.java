package com.colegio.msasignaturas.config;

import com.colegio.msasignaturas.model.Asignatura;
import com.colegio.msasignaturas.repository.AsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AsignaturaRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        
        repository.save(new Asignatura(null, "Matematicas", "Ciencias Exactas"));
        repository.save(new Asignatura(null, "Lenguaje y Comunicacion", "Humanidades"));
        repository.save(new Asignatura(null, "Historia y Geografia", "Humanidades"));
        repository.save(new Asignatura(null, "Biologia", "Ciencias Exactas"));
        repository.save(new Asignatura(null, "Educacion Fisica", "Deportes"));
    }
}