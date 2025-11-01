package com.tomates.naruto.entity;
import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Aldea {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private String nacion;

    @OneToMany(mappedBy = "aldea", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Ninja> ninjas;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNacion() { return nacion; }
    public void setNacion(String nacion) { this.nacion = nacion; }

    public List<Ninja> getNinjas() { return ninjas; }
    public void setNinjas(List<Ninja> ninjas) { this.ninjas = ninjas; }
    
}