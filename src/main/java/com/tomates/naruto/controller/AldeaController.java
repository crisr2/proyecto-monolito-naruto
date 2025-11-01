package com.tomates.naruto.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomates.naruto.dto.AldeaDTO;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.mapper.EntityMapper;
import com.tomates.naruto.service.AldeaService;

@RestController
@RequestMapping("/api/aldeas")
@CrossOrigin(origins = "*")
public class AldeaController {

    private final AldeaService aldeaService;
    private final EntityMapper mapper;

    // 🔹 Inyectamos también el mapper
    public AldeaController(AldeaService aldeaService, EntityMapper mapper) {
        this.aldeaService = aldeaService;
        this.mapper = mapper;
    }

    // 🔹 Registrar Aldea (sin DTO porque el cliente envía la entidad completa)
    @PostMapping
    public ResponseEntity<Aldea> crearAldea(@RequestBody Aldea aldea) {
        Aldea nueva = aldeaService.registrarAldea(aldea);
        return ResponseEntity.ok(nueva);
    }

    // 🔹 Listar todas las aldeas -> usando mapper
    @GetMapping
    public ResponseEntity<List<AldeaDTO>> listarAldeas() {
        List<AldeaDTO> aldeas = aldeaService.listarAldeas()
                .stream()
                .map(mapper::toAldeaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(aldeas);
    }

    // 🔹 Obtener una aldea específica -> usando mapper
    @GetMapping("/{id}")
    public ResponseEntity<AldeaDTO> obtenerAldea(@PathVariable Long id) {
        Aldea aldea = aldeaService.obtenerAldea(id);
        if (aldea == null) {
            return ResponseEntity.notFound().build();
        }
        AldeaDTO dto = mapper.toAldeaDTO(aldea);
        return ResponseEntity.ok(dto);
    }

    // 🔹 Eliminar aldea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAldea(@PathVariable Long id) {
        aldeaService.eliminarAldea(id);
        return ResponseEntity.noContent().build();
    }
}
