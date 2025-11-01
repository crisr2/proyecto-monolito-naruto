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

    public List<Mision> listarMisiones() {
        return misionRepository.findAll();
    }

    public Mision obtenerMision(Long id) {
        return misionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Misión no encontrada con ID: " + id));
    }

    public Mision registrarMision(Mision mision) {
        if (mision.getRango() != null) {
            mision.setRangoMinimo(mision.getRango().rangoMinimoPorMision());
        }
        return misionRepository.save(mision);
    }

    public void eliminarMision(Long id) {
        misionRepository.deleteById(id);
    }

    public Mision asignarNinja(Long misionId, Long ninjaId) {
        Mision mision = obtenerMision(misionId);
        Ninja ninja = ninjaRepository.findById(ninjaId)
                .orElseThrow(() -> new RuntimeException("Ninja no encontrado con ID: " + ninjaId));

        // Regla 1: Máximo 3 participantes
        if (mision.getParticipantes().size() >= 3) {
            throw new RuntimeException("La misión ya tiene el máximo de 3 participantes.");
        }

        // Regla 2: Validar rango mínimo
        if (ninja.getRango().ordinal() < mision.getRangoMinimo().ordinal()) {
            throw new RuntimeException("El rango del ninja es demasiado bajo para esta misión.");
        }

        // Regla 3: Si misión tiene rango C, un Genin sólo puede participar si hay un Jonin
        if (mision.getRango() == RangoMision.C && ninja.getRango() == RangoNinja.GENIN) {
            boolean hayJonin = mision.getParticipantes()
                    .stream()
                    .anyMatch(p -> p.getRango() == RangoNinja.JONIN);
            if (!hayJonin) {
                throw new RuntimeException("Un Genin solo puede participar si hay un Jonin en la misión tipo C.");
            }
        }

        // Regla 4: Evitar duplicar ninjas
        if (mision.getParticipantes().contains(ninja)) {
            throw new RuntimeException("Este ninja ya está asignado a la misión.");
        }

        mision.getParticipantes().add(ninja);
        return misionRepository.save(mision);
    }

}