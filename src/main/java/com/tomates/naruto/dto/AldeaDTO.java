package com.tomates.naruto.dto;

import java.util.List;

public class AldeaDTO {
    private Long id;
    private String nombre;
    private String nacion;
    private List<String> ninjas; // Solo los nombres o IDs

    public AldeaDTO(Long id, String nombre, String nacion, List<String> ninjas) {
        this.id = id;
        this.nombre = nombre;
        this.nacion = nacion;
        this.ninjas = ninjas;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNacion() { return nacion; }
    public List<String> getNinjas() { return ninjas; }
}
