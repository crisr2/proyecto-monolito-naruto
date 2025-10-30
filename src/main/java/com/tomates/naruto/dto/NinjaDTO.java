package com.tomates.naruto.dto;

import java.util.List;

public class NinjaDTO {
    private Long id;
    private String nombre;
    private String rango;
    private int ataque;
    private int defensa;
    private int chakra;
    private String aldea; // Nombre de la aldea
    private List<String> jutsus; // Nombres de los jutsus

    public NinjaDTO(Long id, String nombre, String rango, int ataque, int defensa, int chakra, String aldea, List<String> jutsus) {
        this.id = id;
        this.nombre = nombre;
        this.rango = rango;
        this.ataque = ataque;
        this.defensa = defensa;
        this.chakra = chakra;
        this.aldea = aldea;
        this.jutsus = jutsus;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRango() { return rango; }
    public int getAtaque() { return ataque; }
    public int getDefensa() { return defensa; }
    public int getChakra() { return chakra; }
    public String getAldea() { return aldea; }
    public List<String> getJutsus() { return jutsus; }
}
