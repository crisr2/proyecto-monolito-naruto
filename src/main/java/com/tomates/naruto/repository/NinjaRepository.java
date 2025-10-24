package com.tomates.naruto.repository;
import com.tomates.naruto.entity.Ninja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NinjaRepository extends JpaRepository<Ninja, Long> {

    // Busqueda por rango
    List<Ninja> findByRango(String rango);

    // Busqueda por nombre
    List<Ninja> findByNombreContainingIgnoreCase(String nombre);
}
