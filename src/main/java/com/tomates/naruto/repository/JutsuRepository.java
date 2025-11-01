package com.tomates.naruto.repository;
import com.tomates.naruto.entity.Jutsu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JutsuRepository extends JpaRepository<Jutsu, Long> {
    // Buscar jutsus por tipo
    List<Jutsu> findByTipo(String tipo);
    
    // Buscar jutsus por nombre ignorando mayúsculas o minúsculas
    List<Jutsu> findByNombreContainingIgnoreCase(String nombre);
}
