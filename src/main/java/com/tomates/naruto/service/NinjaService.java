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

    public Ninja registrarNinja(Ninja ninja) {
        return ninjaRepository.save(ninja);
    }

    public List<Ninja> listarNinjas() {
        return ninjaRepository.findAll();
    }

    public Ninja obtenerNinja(Long id) {
        return ninjaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ninja no encontrado con ID: " + id));
    }

    public Ninja actualizarNinja(Long id, Ninja ninjaActualizado) {
        Ninja existente = obtenerNinja(id);
        existente.setNombre(ninjaActualizado.getNombre());
        existente.setRango(ninjaActualizado.getRango());
        existente.setAtaque(ninjaActualizado.getAtaque());
        existente.setDefensa(ninjaActualizado.getDefensa());
        existente.setChakra(ninjaActualizado.getChakra());
        existente.setAldea(ninjaActualizado.getAldea());
        existente.setJutsus(ninjaActualizado.getJutsus());
        return ninjaRepository.save(existente);
    }

    public void eliminarNinja(Long id) {
        ninjaRepository.deleteById(id);
    }
}
