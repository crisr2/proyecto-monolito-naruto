package com.tomates.naruto.config;
import com.tomates.naruto.entity.Aldea;
import com.tomates.naruto.repository.AldeaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AldeaInitializer {

    private final AldeaRepository aldeaRepository;

    public AldeaInitializer(AldeaRepository aldeaRepository) {
        this.aldeaRepository = aldeaRepository;
    }

    @PostConstruct
    public void init() {
        // Si ya existen aldeas, no hace nada
        if (aldeaRepository.count() > 0) {
            return;
        }

        Aldea konoha = new Aldea();
        konoha.setNombre("Konoha");
        konoha.setNacion("País del Fuego");
    
        Aldea suna = new Aldea();
        suna.setNombre("Suna");
        suna.setNacion("País del Viento");

        Aldea kiri = new Aldea();
        kiri.setNombre("Kiri");
        kiri.setNacion("País del Agua");

        Aldea kumo = new Aldea();
        kumo.setNombre("Kumo");
        kumo.setNacion("País del Rayo");

        Aldea iwa = new Aldea();
        iwa.setNombre("Iwa");
        iwa.setNacion("País de la Tierra");

        aldeaRepository.saveAll(List.of(konoha, suna, kiri, kumo, iwa));

        System.out.println("Aldeas iniciales cargadas correctamente.");
    }
}
