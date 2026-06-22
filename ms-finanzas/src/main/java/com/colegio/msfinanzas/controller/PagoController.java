package com.colegio.msfinanzas.controller;

import com.colegio.msfinanzas.dto.PagoRequestDTO;
import com.colegio.msfinanzas.dto.PagoResponseDTO;
import com.colegio.msfinanzas.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagoResponseDTO crear(@RequestBody PagoRequestDTO request) {
        return service.crear(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PagoResponseDTO> listar() {
        return service.listarTodos();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagoResponseDTO actualizar(@PathVariable Long id, @RequestBody PagoRequestDTO request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}