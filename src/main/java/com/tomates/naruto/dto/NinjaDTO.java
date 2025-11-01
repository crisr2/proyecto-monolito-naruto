package com.tomates.naruto.dto;
import java.util.List;

public class NinjaDTO {
    private Long id;
    private String nombre;
    private String rango;
    private int ataque;
    private int defensa;
    private int chakra;
    private String aldeaNombre; 
    private List<String> jutsus; 

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }  

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public int getChakra() { return chakra; }
    public void setChakra(int chakra) { this.chakra = chakra; }

    public String getAldeaNombre() { return aldeaNombre; }
    public void setAldeaNombre(String aldeaNombre) { this.aldeaNombre = aldeaNombre; }

    public List<String> getJutsus() { return jutsus; }
    public void setJutsus(List<String> jutsus) { this.jutsus = jutsus; }
}
