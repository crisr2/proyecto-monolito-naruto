package com.tomates.naruto.repository;
import com.tomates.naruto.entity.Jutsu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JutsuRepository extends JpaRepository<Jutsu, Long> {
    List<Jutsu> findByTipo(String tipo);
    List<Jutsu> findByNombreContainingIgnoreCase(String nombre);
}
