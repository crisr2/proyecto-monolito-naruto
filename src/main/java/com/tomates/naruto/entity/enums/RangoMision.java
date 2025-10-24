package com.tomates.naruto.entity.enums;

public enum RangoMision {
    D, C, B, A, S;

    public RangoNinja rangoMinimoPorMision() {
        return switch (this) {
            case D -> RangoNinja.GENIN;
            case C -> RangoNinja.CHUNIN;
            case B -> RangoNinja.CHUNIN;
            case A -> RangoNinja.JONIN;
            case S -> RangoNinja.JONIN;
        };
    }

    public String getDescripcion() {
        return switch (this) {
            case D -> "Misión de dificultad baja";
            case C -> "Misión de dificultad moderada";
            case B -> "Misión peligrosa";
            case A -> "Misión de alto riesgo";
            case S -> "Misión legendaria";
        };
    }
}