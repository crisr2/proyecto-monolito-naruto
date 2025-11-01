package com.tomates.naruto.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tomates.naruto.entity.Mision;
import com.tomates.naruto.entity.enums.RangoMision;

@Repository
public interface MisionRepository extends JpaRepository<Mision, Long> {

    // Buscar misiones por rango
    List<Mision> findByRango(RangoMision rango);

    // Buscar misiones con recompensa mínima
    List<Mision> findByRecompensaGreaterThan(int recompensa);
}
