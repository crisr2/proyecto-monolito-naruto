package com.tomates.naruto.controller;
import com.tomates.naruto.entity.Jutsu;
import com.tomates.naruto.service.JutsuService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jutsus")
@CrossOrigin(origins = "*")
public class JutsuController {

    private final JutsuService jutsuService;

    public JutsuController(JutsuService jutsuService) {
        this.jutsuService = jutsuService;
    }

    @GetMapping
    public List<Jutsu> listarTodos() {
        return jutsuService.listarJutsus();
    }

    @GetMapping("/{id}")
    public Jutsu obtenerPorId(@PathVariable Long id) {
        return jutsuService.obtenerPorId(id);
    }

    @PostMapping
    public Jutsu crear(@RequestBody Jutsu jutsu) {
        return jutsuService.registrarJutsu(jutsu);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        jutsuService.eliminar(id);
    }
}
