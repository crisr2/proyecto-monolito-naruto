package com.tomates.naruto.controller;
import com.tomates.naruto.entity.Ninja;
import com.tomates.naruto.service.NinjaService;
import com.tomates.naruto.repository.AldeaRepository;
import com.tomates.naruto.entity.Aldea;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ninjas")
@CrossOrigin(origins = "*")
public class NinjaController {

    private final NinjaService ninjaService;
    private final AldeaRepository aldeaRepository;

    public NinjaController(NinjaService ninjaService, AldeaRepository aldeaRepository) {
        this.ninjaService = ninjaService;
        this.aldeaRepository = aldeaRepository;
    }

    @GetMapping
    public List<Ninja> listarTodos() {
        return ninjaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Ninja obtenerPorId(@PathVariable Long id) {
        return ninjaService.obtenerPorId(id);
    }

    @PostMapping
    public Ninja crear(@RequestBody Ninja ninja) {
        if (ninja.getAldea() != null && ninja.getAldea().getId() != null) {
            Aldea aldea = aldeaRepository.findById(ninja.getAldea().getId())
                .orElseThrow(() -> new RuntimeException("Aldea no encontrada"));
            ninja.setAldea(aldea);
        }
        return ninjaService.guardar(ninja);
    }

    @PutMapping("/{id}")
    public Ninja actualizar(@PathVariable Long id, @RequestBody Ninja ninjaActualizado) {
        if (ninjaActualizado.getAldea() != null && ninjaActualizado.getAldea().getId() != null) {
            Aldea aldea = aldeaRepository.findById(ninjaActualizado.getAldea().getId())
                    .orElseThrow(() -> new RuntimeException("Aldea no encontrada"));
        ninjaActualizado.setAldea(aldea);
    }
    return ninjaService.actualizar(id, ninjaActualizado);
}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ninjaService.eliminar(id);
    }
}