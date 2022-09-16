package com.esiljak.helpers;

import com.esiljak.exceptions.IllegalKeyFormatException;

import java.util.List;

public class StringParser {
    private static final String ILLEGAL_SENTENCE = "Illegal sentence format to parse";

    private static void checkValidityOfArray(String[] array) throws IllegalKeyFormatException {
        if (array.length != 2)
            throw new IllegalKeyFormatException(ILLEGAL_SENTENCE);
    }

    public static List<String> getKeyValuePair(String sentence) throws IllegalKeyFormatException {
        String[] stringArray = sentence.split("is");
        checkValidityOfArray(stringArray);
        return List.of(stringArray[0].trim(), stringArray[1].trim());
    }
}
