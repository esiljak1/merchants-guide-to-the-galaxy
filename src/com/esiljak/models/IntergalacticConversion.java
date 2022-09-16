package com.esiljak.models;

import com.esiljak.exceptions.DuplicatedConversionKeyException;
import com.esiljak.exceptions.DuplicatedConversionValueException;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.helpers.Parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntergalacticConversion {
    private static final String DUPLICATED_KEY = "Cannot have multiple entries of the same key";
    private static final String DUPLICATED_VALUE = "Cannot have multiple entries of the same value";
    private static final String ILLEGAL_NUMERAL = "Only valid options for value are I, V, X, L, C, D, M";
    private Map<String, String> entries;

    private void validateNewEntry(String key, String value) throws DuplicatedConversionKeyException, DuplicatedConversionValueException, IllegalRomanNumeralException {
        if (entries.containsKey(key))
            throw new DuplicatedConversionKeyException(DUPLICATED_KEY);
        if (entries.containsValue(value))
            throw new DuplicatedConversionValueException(DUPLICATED_VALUE);

        Parser.checkValidDigit(value);
    }

    public IntergalacticConversion() {
        entries = new HashMap<>();
    }

    public IntergalacticConversion(Map<String, String> entries) {
        this.entries = entries;
    }

    public Map<String, String> getEntries() {
        return Collections.unmodifiableMap(entries);
    }

    public void setEntries(Map<String, String> entries) {
        this.entries = entries;
    }

    public void addEntry(String key, String value) throws DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        validateNewEntry(key, value);
        entries.put(key, value);
    }
}
