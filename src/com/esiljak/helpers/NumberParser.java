package com.esiljak.helpers;

import com.esiljak.exceptions.IllegalRomanNumeralException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumberParser {
    private static final String ILLEGAL_OCCURRENCES = "Illegal number of occurrences of a single character";
    private static final String ILLEGAL_SEQUENCE = "Illegal sequence of characters";
    private static final String ILLEGAL_CHARACTER = "Illegal character in a sequence";
    private static final Map<String, Integer> digits = Stream.of(new Object[][] {
            { "I", 1 },
            { "V", 5 },
            { "X", 10 },
            { "L", 50 },
            { "C", 100 },
            { "D", 500 },
            { "M", 1000 }
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (int) data[1]));

    private static boolean isValidPrefixDigit(String prefix, String current){
        return (digits.get(current) / digits.get(prefix)) == 10 ||
                (digits.get(current) / digits.get(prefix)) == 5;
    }

    private static boolean isValidSuffixDigit(String prefix, String suffix){
        return digits.get(prefix) > digits.get(suffix);
    }

    private static void checkNumberOfCharactersValid(String romanNumber) throws IllegalRomanNumeralException {
        long numberOfI = romanNumber.chars().filter(ch -> ch == 'I').count();
        long numberOfV = romanNumber.chars().filter(ch -> ch == 'V').count();
        long numberOfX = romanNumber.chars().filter(ch -> ch == 'X').count();
        long numberOfL = romanNumber.chars().filter(ch -> ch == 'L').count();
        long numberOfC = romanNumber.chars().filter(ch -> ch == 'C').count();
        long numberOfD = romanNumber.chars().filter(ch -> ch == 'D').count();
        long numberOfM = romanNumber.chars().filter(ch -> ch == 'M').count();

        if(numberOfI > 4 || numberOfX > 4 || numberOfC > 4 || numberOfM > 4
                || numberOfV > 1 || numberOfL > 1 || numberOfD > 1)
            throw new IllegalRomanNumeralException(ILLEGAL_OCCURRENCES);
    }

    private static void checkValiditySameCharacters(boolean isPrefix) throws IllegalRomanNumeralException {
        if(isPrefix)
            throw new IllegalRomanNumeralException(ILLEGAL_SEQUENCE);
    }

    private static void checkValiditySubtractiveCharacters(boolean isPrefix, int numberOfRepeatedCharacters,
                                                           String current, String next) throws IllegalRomanNumeralException {
        if(isPrefix || numberOfRepeatedCharacters != 1 || !isValidPrefixDigit(current, next))
            throw new IllegalRomanNumeralException(ILLEGAL_SEQUENCE);
    }

    private static void checkValidityAdditiveCharacters(boolean isPrefix, String prefixDigit, String next) throws IllegalRomanNumeralException {
        if(isPrefix && !isValidSuffixDigit(prefixDigit, next))
            throw new IllegalRomanNumeralException(ILLEGAL_SEQUENCE);
    }

    private static void checkRepeatedCharacters(int numberOfRepeatedCharacters) throws IllegalRomanNumeralException {
        if(numberOfRepeatedCharacters > 3)
            throw new IllegalRomanNumeralException(ILLEGAL_OCCURRENCES);
    }

    private static void checkValidityOfRomanNumber(String romanNumber) throws IllegalRomanNumeralException {
        checkNumberOfCharactersValid(romanNumber);
        int numberOfRepeatedCharacters = 1;
        boolean isPrefix = false;
        String prefixDigit = "";

        for(int i = 0; i < romanNumber.length() - 1; i++){
            String current = romanNumber.charAt(i) + "";
            String next = romanNumber.charAt(i + 1) + "";
            checkValidDigit(current);
            checkValidDigit(next);

            if(current.equals(next)){
                checkValiditySameCharacters(isPrefix);
                numberOfRepeatedCharacters++;
            }else if(digits.get(current) < digits.get(next)){
                checkValiditySubtractiveCharacters(isPrefix, numberOfRepeatedCharacters, current, next);
                isPrefix = true;
                prefixDigit = current;
            }else{
                checkValidityAdditiveCharacters(isPrefix, prefixDigit, next);
                isPrefix = false;
                numberOfRepeatedCharacters = 1;
            }
            checkRepeatedCharacters(numberOfRepeatedCharacters);
        }
    }

    public static void checkValidDigit(String digit) throws IllegalRomanNumeralException {
        if(digit.length() != 1 || !digits.containsKey(digit))
            throw new IllegalRomanNumeralException(ILLEGAL_CHARACTER);
    }

    public static int parseRomanNumeral(String numeral) throws IllegalRomanNumeralException {
        checkValidityOfRomanNumber(numeral);
        int value = 0;
        for(int i = 0; i < numeral.length(); i++){
            String current = numeral.charAt(i) + "";
            String next = i + 1 < numeral.length() ? numeral.charAt(i + 1) + "" : "";
            if(i + 1 < numeral.length() && digits.get(current) < digits.get(next)){
                value -= digits.get(current);
            }else{
                value += digits.get(current);
            }
        }
        return value;
    }
}
