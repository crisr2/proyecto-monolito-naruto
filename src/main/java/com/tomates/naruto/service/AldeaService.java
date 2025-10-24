package com.tomates.naruto.service;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.repository.AldeaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AldeaService {

    private final AldeaRepository aldeaRepository;

    public AldeaService(AldeaRepository aldeaRepository) {
        this.aldeaRepository = aldeaRepository;
    }

    public Aldea registrarAldea(Aldea aldea) {
        return aldeaRepository.save(aldea);
    }

    public List<Aldea> listarAldeas() {
        return aldeaRepository.findAll();
    }

    public Aldea obtenerAldea(Long id) {
        return aldeaRepository.findById(id).orElse(null);
    }

    public void eliminarAldea(Long id) {
        aldeaRepository.deleteById(id);
    }
}