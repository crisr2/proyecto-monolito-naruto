package com.tomates.naruto.service;
import com.tomates.naruto.entity.Ninja;
import com.tomates.naruto.repository.NinjaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;

    public NinjaService(NinjaRepository ninjaRepository) {
        this.ninjaRepository = ninjaRepository;
    }

    // Obtener todos los ninjas
    public List<Ninja> obtenerTodos() {
        return ninjaRepository.findAll();
    }

    // Obtener ninja por ID
    public Ninja obtenerPorId(Long id) {
        return ninjaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ninja no encontrado con ID: " + id));
    }

    // Crear o actualizar ninja
    public Ninja guardar(Ninja ninja) {
        return ninjaRepository.save(ninja);
    }

    // Actualizar ninja existente
    public Ninja actualizar(Long id, Ninja ninjaActualizado) {
        Ninja existente = obtenerPorId(id);
        existente.setNombre(ninjaActualizado.getNombre());
        existente.setRango(ninjaActualizado.getRango());
        existente.setAtaque(ninjaActualizado.getAtaque());
        existente.setDefensa(ninjaActualizado.getDefensa());
        existente.setChakra(ninjaActualizado.getChakra());
        existente.setAldea(ninjaActualizado.getAldea());
        existente.setJutsus(ninjaActualizado.getJutsus());
        return ninjaRepository.save(existente);
    }

    // Eliminar ninja
    public void eliminar(Long id) {
        ninjaRepository.deleteById(id);
    }
}
