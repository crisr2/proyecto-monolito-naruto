package com.tomates.naruto.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tomates.naruto.entity.enums.RangoNinja;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Ninja {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    @Enumerated(EnumType.STRING)
    private RangoNinja rango;
    private int ataque;
    private int defensa;
    private int chakra;

    @ManyToOne
    @JsonBackReference
    private Aldea aldea;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Jutsu> jutsus;

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
    public RangoNinja getRango() {
        return rango;
    }
    public void setRango(RangoNinja rango) {
        this.rango = rango;
    }
    public int getAtaque() {
        return ataque;
    }
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
    public int getDefensa() {
        return defensa;
    }
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
    public int getChakra() {
        return chakra;
    }
    public void setChakra(int chakra) {
        this.chakra = chakra;
    }
    public Aldea getAldea() {
        return aldea;
    }
    public void setAldea(Aldea aldea) {
        this.aldea = aldea;
    }
    public List<Jutsu> getJutsus() {
        return jutsus;
    }
    public void setJutsus(List<Jutsu> jutsus) {
        this.jutsus = jutsus;
    }

}