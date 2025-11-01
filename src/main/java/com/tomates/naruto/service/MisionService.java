package com.tomates.naruto.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.tomates.naruto.entity.Mision;
import com.tomates.naruto.entity.Ninja;
import com.tomates.naruto.entity.enums.RangoMision;
import com.tomates.naruto.entity.enums.RangoNinja;
import com.tomates.naruto.repository.MisionRepository;
import com.tomates.naruto.repository.NinjaRepository;

@Service
public class MisionService {

    private final MisionRepository misionRepository;
    private final NinjaRepository ninjaRepository;

    public MisionService(MisionRepository misionRepository, NinjaRepository ninjaRepository) {
        this.misionRepository = misionRepository;
        this.ninjaRepository = ninjaRepository;
    }

    // Listar todas las misiones
    public List<Mision> obtenerTodas() {
        return misionRepository.findAll();
    }

    // Obtener una misión por ID
    public Mision obtenerPorId(Long id) {
        return misionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada con ID: " + id));
    }

    // Crear misión nueva
    public Mision guardar(Mision mision) {
        // Asignar rango mínimo automáticamente según dificultad
        if (mision.getRango() != null) {
            mision.setRangoMinimo(mision.getRango().rangoMinimoPorMision());
        }
        return misionRepository.save(mision);
    }

    // Eliminar misión
    public void eliminar(Long id) {
        misionRepository.deleteById(id);
    }

    // Reglas para asignar ninja a una misión 
    public Mision asignarNinja(Long misionId, Long ninjaId) {
        Mision mision = obtenerPorId(misionId);
        Ninja ninja = ninjaRepository.findById(ninjaId)
                .orElseThrow(() -> new RuntimeException("Ninja no encontrado con ID: " + ninjaId));

        // Regla 1: máximo 3 participantes
        if (mision.getParticipantes().size() >= 3) {
            throw new RuntimeException("La misión ya tiene el máximo de 3 participantes.");
        }

        // Regla 2: validar rango mínimo
        if (ninja.getRango().ordinal() < mision.getRangoMinimo().ordinal()) {
            throw new RuntimeException("El rango del ninja es demasiado bajo para esta misión.");
        }

        // Regla 3: si misión C, un Genin sólo puede participar si hay un Jonin
        if (mision.getRango() == RangoMision.C && ninja.getRango() == RangoNinja.GENIN) {
            boolean hayJonin = mision.getParticipantes()
                    .stream()
                    .anyMatch(p -> p.getRango() == RangoNinja.JONIN);
            if (!hayJonin) {
                throw new RuntimeException("Un Genin solo puede participar si hay un Jonin en la misión tipo C.");
            }
        }

        // Regla 4: evitar duplicar ninja
        if (mision.getParticipantes().contains(ninja)) {
            throw new RuntimeException("Este ninja ya está asignado a la misión.");
        }

        // Tras validar, asignar ninja a la misión
        mision.getParticipantes().add(ninja);
        return misionRepository.save(mision);
    }

}