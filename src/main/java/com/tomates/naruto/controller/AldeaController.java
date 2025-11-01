package com.tomates.naruto.controller;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public AldeaController(AldeaService aldeaService, EntityMapper mapper) {
        this.aldeaService = aldeaService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Aldea> registrarAldea(@RequestBody Aldea aldea) {
        Aldea nueva = aldeaService.registrarAldea(aldea);
        return ResponseEntity.ok(nueva);
    }

    @GetMapping
    public ResponseEntity<List<AldeaDTO>> listarAldeas() {
        List<AldeaDTO> aldeas = aldeaService.listarAldeas()
                .stream()
                .map(mapper::toAldeaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(aldeas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AldeaDTO> obtenerAldea(@PathVariable Long id) {
        Aldea aldea = aldeaService.obtenerAldea(id);
        if (aldea == null) {
            return ResponseEntity.notFound().build();
        }
        AldeaDTO dto = mapper.toAldeaDTO(aldea);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAldea(@PathVariable Long id) {
        aldeaService.eliminarAldea(id);
        return ResponseEntity.noContent().build();
    }
}
