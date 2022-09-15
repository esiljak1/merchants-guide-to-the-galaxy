package com.esiljak.models;

public class RomanNumeral {
    private String numeral;
    private int value = 0;

    public RomanNumeral(String numeral) {
        this.numeral = numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return numeral;
    }
}
