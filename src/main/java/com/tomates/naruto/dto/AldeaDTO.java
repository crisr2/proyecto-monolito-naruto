package com.tomates.naruto.dto;
import java.util.List;

public class AldeaDTO {
    private Long id;
    private String nombre;
    private String nacion;
    private List<String> ninjas;

    // Getters y Setters
    
    public Long getId() { return id; }
    public Long setId(Long id) { return this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacion() { return nacion; }
    public void setNacion(String nacion) { this.nacion = nacion; }
    
    public List<String> getNinjas() { return ninjas; }
    public void setNinjas(List<String> ninjas) { this.ninjas = ninjas; }
}
