package com.tomates.naruto.controller;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.service.AldeaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/aldeas")
@CrossOrigin
public class AldeaController {

    private final AldeaService aldeaService;

    public AldeaController(AldeaService aldeaService) {
        this.aldeaService = aldeaService;
    }

    @PostMapping
    public ResponseEntity<Aldea> crearAldea(@RequestBody Aldea aldea) {
        return ResponseEntity.ok(aldeaService.registrarAldea(aldea));
    }

    @GetMapping
    public ResponseEntity<List<Aldea>> listarAldeas() {
        return ResponseEntity.ok(aldeaService.listarAldeas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aldea> obtenerAldea(@PathVariable Long id) {
        Aldea aldea = aldeaService.obtenerAldea(id);
        return (aldea != null) ? ResponseEntity.ok(aldea) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAldea(@PathVariable Long id) {
        aldeaService.eliminarAldea(id);
        return ResponseEntity.noContent().build();
    }
}