package com.tomates.naruto.repository;
import com.tomates.naruto.entity.Aldea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AldeaRepository extends JpaRepository<Aldea, Long> {

    // Buscar por nombre exacto
    Aldea findByNombre(String nombre);
}
