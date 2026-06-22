package com.colegio.msasignaturas.controller;

import com.colegio.msasignaturas.dto.AsignaturaRequestDTO;
import com.colegio.msasignaturas.dto.AsignaturaResponseDTO;
import com.colegio.msasignaturas.service.AsignaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")                                                                                                                                         
@RequiredArgsConstructor
public class AsignaturaController {

    private final AsignaturaService service; 

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) 
    public AsignaturaResponseDTO crear(@RequestBody AsignaturaRequestDTO request) {

        return service.crearAsignatura(request);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK) 
    public List<AsignaturaResponseDTO> listar() {

        return service.listarTodas();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AsignaturaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AsignaturaResponseDTO actualizar(@PathVariable Long id, @RequestBody AsignaturaRequestDTO request) {
        return service.actualizarAsignatura(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminarAsignatura(id);
    }
}