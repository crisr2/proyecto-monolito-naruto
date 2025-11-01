package com.tomates.naruto.dto;

import java.util.List;

public class NinjaDTO {
    private Long id;
    private String nombre;
    private String rango;
    private int ataque;
    private int defensa;
    private int chakra;
    private String aldeaNombre; // Nombre de la aldea
    private List<String> jutsus; // Nombres de los jutsus

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRango() { return rango; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getChakra() { return chakra; }
    public String getAldeaNombre() { return aldeaNombre; }
    public List<String> getJutsus() { return jutsus; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRango(String rango) { this.rango = rango; }  
    public void setAtaque(int ataque) { this.ataque = ataque; }
    public void setDefensa(int defensa) { this.defensa = defensa; }
    public void setChakra(int chakra) { this.chakra = chakra; }
    public void setAldeaNombre(String aldeaNombre) { this.aldeaNombre = aldeaNombre; }
    public void setJutsus(List<String> jutsus) { this.jutsus = jutsus; }
}
