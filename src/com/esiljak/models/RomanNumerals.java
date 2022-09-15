package com.esiljak.models;

public enum RomanNumerals {
    I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

    private int value;

    RomanNumerals(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    @Override
    public String toString() {
        return this.name();
    }
}
