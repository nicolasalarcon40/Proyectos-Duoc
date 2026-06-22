package com.colegio.msestudiantes.config;

import com.colegio.msestudiantes.model.Estudiante;
import com.colegio.msestudiantes.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EstudianteRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        repository.save(new Estudiante(null, "19055586-0", "Nicolas", "Alarcon", 28));
        repository.save(new Estudiante(null, "20111222-3", "Katherine", "Perez", 29));
        repository.save(new Estudiante(null, "25444555-6", "Juan", "Gomez", 22));
    }
}