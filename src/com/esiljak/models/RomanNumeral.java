package com.esiljak.models;

import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.helpers.NumberParser;

public class RomanNumeral {
    private String numeral;
    private int value;

    public RomanNumeral(String numeral) throws IllegalRomanNumeralException {
        value = NumberParser.parseRomanNumeral(numeral);
        this.numeral = numeral;
    }

    public void setNumeral(String numeral) throws IllegalRomanNumeralException {
        value = NumberParser.parseRomanNumeral(numeral);
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
