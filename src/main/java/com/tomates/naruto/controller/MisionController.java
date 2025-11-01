package com.tomates.naruto.controller;
import com.tomates.naruto.dto.MisionDTO;
import com.tomates.naruto.entity.Mision;
import com.tomates.naruto.mapper.EntityMapper;
import com.tomates.naruto.service.MisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/misiones")
@CrossOrigin(origins = "*")
public class MisionController {

    private final MisionService misionService;
    private final EntityMapper mapper;

    public MisionController(MisionService misionService, EntityMapper mapper) {
        this.misionService = misionService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<MisionDTO>> listarTodas() {
        List<MisionDTO> misiones = misionService.listarMisiones()
                .stream()
                .map(mapper::toMisionDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(misiones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MisionDTO> obtenerMision(@PathVariable Long id) {
        Mision mision = misionService.obtenerMision(id);
        if (mision == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toMisionDTO(mision));
    }

    @PostMapping
    public ResponseEntity<MisionDTO> crear(@RequestBody Mision mision) {
        Mision guardada = misionService.registrarMision(mision);
        return ResponseEntity.ok(mapper.toMisionDTO(guardada));
    }

    @PostMapping("/{misionId}/{ninjaId}")
    public ResponseEntity<?> asignarNinja(@PathVariable Long misionId, @PathVariable Long ninjaId) {
        try {
            Mision m = misionService.asignarNinja(misionId, ninjaId);
            return ResponseEntity.ok(mapper.toMisionDTO(m));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        misionService.eliminarMision(id);
        return ResponseEntity.noContent().build();
    }
}
