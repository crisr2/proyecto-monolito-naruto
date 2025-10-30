package com.tomates.naruto.dto;

import java.util.List;

public class MisionDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String dificultad;   // o rango, depende de tu entidad Mision
    private int recompensa;
    private String rangoMinimo;
    private List<String> participantes;

    // 🔹 Constructor completo que coincide con tu conversión
    public MisionDTO(Long id, String nombre, String descripcion, String dificultad, int recompensa, String rangoMinimo, List<String> participantes) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.recompensa = recompensa;
        this.rangoMinimo = rangoMinimo;
        this.participantes = participantes;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getDificultad() { return dificultad; }
    public int getRecompensa() { return recompensa; }
    public String getRangoMinimo() { return rangoMinimo; }
    public List<String> getParticipantes() { return participantes; }
}
