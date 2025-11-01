package com.tomates.naruto.controller;
import com.tomates.naruto.dto.NinjaDTO;
import com.tomates.naruto.entity.Ninja;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.entity.Jutsu;
import com.tomates.naruto.mapper.EntityMapper;
import com.tomates.naruto.repository.AldeaRepository;
import com.tomates.naruto.repository.JutsuRepository;
import com.tomates.naruto.service.NinjaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ninjas")
@CrossOrigin(origins = "*")
public class NinjaController {

    private final NinjaService ninjaService;
    private final AldeaRepository aldeaRepository;
    private final JutsuRepository jutsuRepository;
    private final EntityMapper mapper;

    public NinjaController(NinjaService ninjaService, AldeaRepository aldeaRepository,
                           JutsuRepository jutsuRepository, EntityMapper mapper) {
        this.ninjaService = ninjaService;
        this.aldeaRepository = aldeaRepository;
        this.jutsuRepository = jutsuRepository;
        this.mapper = mapper;
    }

    @GetMapping
    public List<NinjaDTO> listarTodos() {
        return ninjaService.listarNinjas()
                .stream()
                .map(mapper::toNinjaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public NinjaDTO obtenerNinja(@PathVariable Long id) {
        return mapper.toNinjaDTO(ninjaService.obtenerNinja(id));
    }

    @PostMapping
    public NinjaDTO crear(@RequestBody Ninja ninja) {
        if (ninja.getAldea() != null && ninja.getAldea().getId() != null) {
            Aldea aldea = aldeaRepository.findById(ninja.getAldea().getId())
                    .orElseThrow(() -> new RuntimeException("Aldea no encontrada"));
            ninja.setAldea(aldea);
        }
        Ninja guardado = ninjaService.registrarNinja(ninja);
        return mapper.toNinjaDTO(guardado);
    }

    @PutMapping("/{id}")
    public NinjaDTO actualizar(@PathVariable Long id, @RequestBody Ninja ninjaActualizado) {
        if (ninjaActualizado.getAldea() != null && ninjaActualizado.getAldea().getId() != null) {
            Aldea aldea = aldeaRepository.findById(ninjaActualizado.getAldea().getId())
                    .orElseThrow(() -> new RuntimeException("Aldea no encontrada"));
            ninjaActualizado.setAldea(aldea);
        }
        Ninja actualizado = ninjaService.actualizarNinja(id, ninjaActualizado);
        return mapper.toNinjaDTO(actualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ninjaService.eliminarNinja(id);
    }

    @PostMapping("/{ninjaId}/{jutsuId}")
    public NinjaDTO asignarJutsu(@PathVariable Long ninjaId, @PathVariable Long jutsuId) {
        Ninja ninja = ninjaService.obtenerNinja(ninjaId);
        if (ninja == null) {
            throw new RuntimeException("Ninja no encontrado");
        }

        Jutsu jutsu = jutsuRepository.findById(jutsuId)
                .orElseThrow(() -> new RuntimeException("Jutsu no encontrado"));

        if (ninja.getJutsus() == null) {
            ninja.setJutsus(new java.util.ArrayList<>());
        }

        if (!ninja.getJutsus().contains(jutsu)) {
            ninja.getJutsus().add(jutsu);
        }

        return mapper.toNinjaDTO(ninjaService.registrarNinja(ninja));
    }
}
