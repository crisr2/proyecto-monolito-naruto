package com.tomates.naruto.entity.enums;

public enum RangoNinja {
    GENIN,
    CHUNIN,
    JONIN;

    public int getNivel() {
        return switch (this) {
            case GENIN -> 1;
            case CHUNIN -> 2;
            case JONIN -> 3;
        };
    }
}