package com.esiljak.helpers;
import com.esiljak.exceptions.IllegalRomanNumeralException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
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
    private static final List<String> singleRepeatCharacters = List.of("V", "L", "D");

    private static boolean isValidPrefixDigit(String prefix, String current){
        return (digits.get(current) / digits.get(prefix)) == 10 ||
                (digits.get(current) / digits.get(prefix)) == 5;
    }

    private static boolean isValidSuffixDigit(String prefix, String suffix){
        return digits.get(prefix) > digits.get(suffix);
    }

    private static boolean isValidDigit(String digit){
        return digits.containsKey(digit);
    }

    private static void checkValidityOfRomanNumber(String romanNumber) throws IllegalRomanNumeralException {
        int numberOfRepeatedCharacters = 1;
        boolean isPrefix = false, isSuffix = false;
        String prefixDigit = "";

        for(int i = 0; i < romanNumber.length() - 1; i++){
            String current = romanNumber.charAt(i) + "";
            String next = romanNumber.charAt(i + 1) + "";
            if((numberOfRepeatedCharacters > 1 && singleRepeatCharacters.contains(current)) || numberOfRepeatedCharacters > 3){
                throw new IllegalRomanNumeralException(ILLEGAL_OCCURRENCES);
            }else if(!isValidDigit(current) || !isValidDigit(next)){
                throw new IllegalRomanNumeralException(ILLEGAL_CHARACTER);
            }

            if(current.equals(next)){
                if(isPrefix)
                    throw new IllegalRomanNumeralException(ILLEGAL_SEQUENCE);

                numberOfRepeatedCharacters++;
            }else if(digits.get(current) < digits.get(next)){
                if(isPrefix || isSuffix || numberOfRepeatedCharacters != 1 || !isValidPrefixDigit(current, next))
                    throw new IllegalRomanNumeralException(ILLEGAL_SEQUENCE);

                isPrefix = true;
                prefixDigit = current;
            }else{
                if(isPrefix && !isValidSuffixDigit(prefixDigit, next))
                    throw new IllegalRomanNumeralException(ILLEGAL_SEQUENCE);

                isPrefix = false;
                numberOfRepeatedCharacters = 1;
                isSuffix = true;
            }
        }
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
