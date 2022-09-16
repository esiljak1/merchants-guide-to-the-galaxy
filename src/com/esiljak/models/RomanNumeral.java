package com.esiljak.models;

import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.helpers.Parser;

public class RomanNumeral {
    private String numeral;
    private int value;

    public RomanNumeral(String numeral) throws IllegalRomanNumeralException {
        value = Parser.parseRomanNumeral(numeral);
        this.numeral = numeral;
    }

    public void setNumeral(String numeral) throws IllegalRomanNumeralException {
        value = Parser.parseRomanNumeral(numeral);
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
