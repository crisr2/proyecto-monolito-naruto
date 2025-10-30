package com.tomates.naruto.controller;
import com.tomates.naruto.entity.Mision;
import com.tomates.naruto.service.MisionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/misiones")
@CrossOrigin(origins = "*")
public class MisionController {

    private final MisionService misionService;

    public MisionController(MisionService misionService) {
        this.misionService = misionService;
    }

    @GetMapping
    public List<Mision> listarTodas() {
        return misionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Mision obtenerPorId(@PathVariable Long id) {
        return misionService.obtenerPorId(id);
    }

    @PostMapping
    public Mision crear(@RequestBody Mision mision) {
        return misionService.guardar(mision);
    }

    @PostMapping("/{misionId}/asignar/{ninjaId}")
    public Map<String, Object> asignarNinja(@PathVariable Long misionId, @PathVariable Long ninjaId) {
        boolean exito = misionService.asignarNinja(misionId, ninjaId);
        return Map.of(
                "success", exito,
                "message", exito ? "Ninja asignado correctamente" : "No se pudo asignar el ninja"
        );
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        misionService.eliminar(id);
    }
}