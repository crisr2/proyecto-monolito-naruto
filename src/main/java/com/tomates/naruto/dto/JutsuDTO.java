package com.tomates.naruto.dto;

public class JutsuDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private int danio;
    private int chakra;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getDanio() { return danio; }
    public void setDanio(int danio) { this.danio = danio; }

    public int getChakra() { return chakra; }
    public void setChakra(int chakra) { this.chakra = chakra; }
}
