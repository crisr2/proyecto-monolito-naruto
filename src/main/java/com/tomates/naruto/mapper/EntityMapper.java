package com.tomates.naruto.mapper;
import com.tomates.naruto.dto.*;
import com.tomates.naruto.entity.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {

    // ----- NINJA -----
    public NinjaDTO toNinjaDTO(Ninja n) {
        NinjaDTO dto = new NinjaDTO();
        dto.setId(n.getId());
        dto.setNombre(n.getNombre());
        dto.setRango(n.getRango().name());
        dto.setAtaque(n.getAtaque());
        dto.setDefensa(n.getDefensa());
        dto.setChakra(n.getChakra());
        dto.setAldeaNombre(n.getAldea() != null ? n.getAldea().getNombre() : null);
        dto.setJutsus(n.getJutsus() != null
                ? n.getJutsus().stream().map(Jutsu::getNombre).collect(Collectors.toList())
                : List.of());
        return dto;
    }

    // ----- ALDEA -----
    public AldeaDTO toAldeaDTO(Aldea a) {
        AldeaDTO dto = new AldeaDTO();
        dto.setId(a.getId());
        dto.setNombre(a.getNombre());
        dto.setNacion(a.getNacion());
        dto.setNinjas(a.getNinjas() != null
                ? a.getNinjas().stream().map(Ninja::getNombre).collect(Collectors.toList())
                : List.of());
        return dto;
    }

    // ----- MISION -----
    public MisionDTO toMisionDTO(Mision m) {
        MisionDTO dto = new MisionDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setDescripcion(m.getDescripcion());
        dto.setRango(m.getRango().name());
        dto.setRecompensa(m.getRecompensa());
        dto.setRangoMinimo(m.getRangoMinimo().name());
        dto.setParticipantes(m.getParticipantes() != null
                ? m.getParticipantes().stream().map(Ninja::getNombre).collect(Collectors.toList())
                : List.of());
        return dto;
    }

    // ----- JUTSU -----
    public JutsuDTO toJutsuDTO(Jutsu j) {
        JutsuDTO dto = new JutsuDTO();
        dto.setId(j.getId());
        dto.setNombre(j.getNombre());
        dto.setTipo(j.getTipo());
        dto.setDanio(j.getDanio());
        dto.setChakra(j.getChakra());
        return dto;
    }
}
