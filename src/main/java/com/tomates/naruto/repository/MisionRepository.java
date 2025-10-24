package com.tomates.naruto.repository;
import com.tomates.naruto.entity.Mision;
import com.tomates.naruto.entity.enums.RangoMision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MisionRepository extends JpaRepository<Mision, Long> {

    // Buscar misiones por dificultad
    List<Mision> findByDificultad(RangoMision dificultad);

    // Buscar misiones con recompensa mínima
    List<Mision> findByRecompensaGreaterThan(int recompensa);
}
