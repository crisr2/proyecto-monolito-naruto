package com.tomates.naruto.entity;
import java.util.ArrayList;
import java.util.List;
import com.tomates.naruto.entity.enums.RangoMision;
import com.tomates.naruto.entity.enums.RangoNinja;
import jakarta.persistence.*;

@Entity
public class Mision {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private RangoMision rango;
    private int recompensa;
    @Enumerated(EnumType.STRING)
    private RangoNinja rangoMinimo;

    @ManyToMany
    private List<Ninja> participantes = new ArrayList<>();

    public Mision() {}

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }   
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public RangoMision getRango() { return rango; }
    public void setRango(RangoMision rango) { this.rango = rango; }

    public int getRecompensa() { return recompensa; }
    public void setRecompensa(int recompensa) { this.recompensa = recompensa; }

    public RangoNinja getRangoMinimo() { return rangoMinimo; }
    public void setRangoMinimo(RangoNinja rangoMinimo) { this.rangoMinimo = rangoMinimo; }
    
    public List<Ninja> getParticipantes() { return participantes; }
    public void setParticipantes(List<Ninja> participantes) { this.participantes = participantes; }
}