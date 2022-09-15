package com.esiljak.models;

import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.helpers.Parser;

public class RomanNumeral {
    private String numeral;
    private int value = 0;

    public RomanNumeral(String numeral) throws IllegalRomanNumeralException {
        this.numeral = numeral;
        value = Parser.parseRomanNumeral(numeral);
    }

    public void setNumeral(String numeral) throws IllegalRomanNumeralException {
        this.numeral = numeral;
        value = Parser.parseRomanNumeral(numeral);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return numeral;
    }
}
