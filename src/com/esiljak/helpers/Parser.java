package com.esiljak.helpers;
import com.esiljak.exceptions.IllegalRomanNumeralException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    private static final Map<String, Integer> digits = Stream.of(new Object[][] {
            { "I", 1 },
            { "V", 5 },
            { "X", 10 },
            { "L", 50 },
            { "C", 100 },
            { "D", 500 },
            { "M", 1000 }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (int) data[1]));

    private static boolean isDigitValid(String romanDigit){
        return digits.containsKey(romanDigit) || romanDigit.equals("");
    }

    public static int parseRomanNumeral(String numeral) throws IllegalRomanNumeralException {
        int value = 0;
        for(int i = 0; i < numeral.length(); i++){
            String current = numeral.charAt(i) + "";
            String next = i + 1 < numeral.length() ? numeral.charAt(i + 1) + "" : "";
            if(!isDigitValid(current) || !isDigitValid(next)){
                throw new IllegalRomanNumeralException("Illegal roman digit found, legal digits are I, V, X, L, C, D, M");
            }
            if(i + 1 < numeral.length() && digits.get(current) < digits.get(next)){
                value -= digits.get(current);
            }else{
                value += digits.get(current);
            }
        }
        return value;
    }
}
