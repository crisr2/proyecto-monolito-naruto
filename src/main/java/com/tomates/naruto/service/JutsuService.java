package com.tomates.naruto.service;
import org.springframework.stereotype.Service;
import com.tomates.naruto.entity.Jutsu;
import com.tomates.naruto.repository.JutsuRepository;
import java.util.List;

@Service
public class JutsuService {

    private final JutsuRepository jutsuRepository;

    public JutsuService(JutsuRepository jutsuRepository) {
        this.jutsuRepository = jutsuRepository;
    }

    public Jutsu guardar(Jutsu jutsu) {
        return jutsuRepository.save(jutsu);
    }

    public List<Jutsu> obtenerTodos() {
        return jutsuRepository.findAll();
    }

    public Jutsu obtenerPorId(Long id) {
        return jutsuRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        jutsuRepository.deleteById(id);
    }
}
