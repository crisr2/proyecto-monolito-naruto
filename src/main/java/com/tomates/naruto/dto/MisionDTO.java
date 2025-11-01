package com.tomates.naruto.dto;
import java.util.List;

public class MisionDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String rango; 
    private int recompensa;
    private String rangoMinimo;
    private List<String> participantes;

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }

    public int getRecompensa() { return recompensa; }
    public void setRecompensa(int recompensa) { this.recompensa = recompensa; }

    public String getRangoMinimo() { return rangoMinimo; }
    public void setRangoMinimo(String rangoMinimo) { this.rangoMinimo = rangoMinimo; }

    public List<String> getParticipantes() { return participantes; }
    public void setParticipantes(List<String> participantes) { this.participantes = participantes; }
}
