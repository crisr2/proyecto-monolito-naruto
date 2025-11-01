package com.tomates.naruto.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Jutsu {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private String tipo; 
    private int danio; 
    private int chakra;
    
    @ManyToMany(mappedBy = "jutsus")
    @JsonBackReference
    private List<Ninja> ninjas;

    // Getters y Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getDanio() {
        return danio;
    }
    public void setdanio(int danio) { 
        if (danio >= 0) this.danio = danio; 
    }
    public int getChakra() {
        return chakra;
    }
    public void setChakra(int chakra) {
        if (chakra >= 0) this.chakra = chakra;
    }
}