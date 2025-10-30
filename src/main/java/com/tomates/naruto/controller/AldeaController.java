package com.tomates.naruto.controller;

import com.tomates.naruto.dto.AldeaDTO;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.entity.Ninja;
import com.tomates.naruto.service.AldeaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aldeas")
@CrossOrigin
public class AldeaController {

    private final AldeaService aldeaService;

    public AldeaController(AldeaService aldeaService) {
        this.aldeaService = aldeaService;
    }

    // Registrar Aldea (no necesita DTO)
    @PostMapping
    public ResponseEntity<Aldea> crearAldea(@RequestBody Aldea aldea) {
        return ResponseEntity.ok(aldeaService.registrarAldea(aldea));
    }

    // Listar todas las aldeas (convertidas a DTO)
    @GetMapping
    public ResponseEntity<List<AldeaDTO>> listarAldeas() {
        List<AldeaDTO> aldeas = aldeaService.listarAldeas().stream()
                .map(aldea -> new AldeaDTO(
                        aldea.getId(),
                        aldea.getNombre(),
                        aldea.getNacion(),
                        aldea.getNinjas() != null
                                ? aldea.getNinjas().stream()
                                        .map(Ninja::getNombre)
                                        .collect(Collectors.toList())
                                : List.of()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(aldeas);
    }

    // Obtener una aldea específica (convertida a DTO)
    @GetMapping("/{id}")
    public ResponseEntity<AldeaDTO> obtenerAldea(@PathVariable Long id) {
        Aldea aldea = aldeaService.obtenerAldea(id);
        if (aldea == null) {
            return ResponseEntity.notFound().build();
        }

        AldeaDTO dto = new AldeaDTO(
                aldea.getId(),
                aldea.getNombre(),
                aldea.getNacion(),
                aldea.getNinjas() != null
                        ? aldea.getNinjas().stream()
                                .map(Ninja::getNombre)
                                .collect(Collectors.toList())
                        : List.of()
        );

        return ResponseEntity.ok(dto);
    }

    // Eliminar aldea (sin cambio)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAldea(@PathVariable Long id) {
        aldeaService.eliminarAldea(id);
        return ResponseEntity.noContent().build();
    }
}
