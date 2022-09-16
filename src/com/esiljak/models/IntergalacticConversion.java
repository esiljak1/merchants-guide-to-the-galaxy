package com.esiljak.models;

import com.esiljak.exceptions.DuplicatedConversionKeyException;
import com.esiljak.exceptions.DuplicatedConversionValueException;
import com.esiljak.exceptions.IllegalKeyFormatException;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.helpers.Parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IntergalacticConversion {
    private static final String DUPLICATED_KEY = "Cannot have multiple entries of the same key";
    private static final String DUPLICATED_VALUE = "Cannot have multiple entries of the same value";
    private static final String ILLEGAL_FORMAT = "Illegal key format detected";
    private Map<String, String> entries;

    private void validateNewEntry(String key, String value) throws DuplicatedConversionKeyException, DuplicatedConversionValueException, IllegalRomanNumeralException {
        if (entries != null && entries.containsKey(key))
            throw new DuplicatedConversionKeyException(DUPLICATED_KEY);
        if (entries != null && entries.containsValue(value))
            throw new DuplicatedConversionValueException(DUPLICATED_VALUE);

        Parser.checkValidDigit(value);
    }

    private void validateKeyFormat(String key) throws IllegalKeyFormatException {
        if (key == null || key.trim().isEmpty() || key.split(" ").length != 1)
            throw new IllegalKeyFormatException(ILLEGAL_FORMAT);
    }

    private void validateEntriesMap(Map<String, String> map) throws DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException, IllegalKeyFormatException {
        for(Map.Entry<String, String> entry : map.entrySet()){
            validateNewEntry(entry.getKey(), entry.getValue());
            validateKeyFormat(entry.getKey());
        }
    }

    public IntergalacticConversion() {
        entries = new HashMap<>();
    }

    public IntergalacticConversion(Map<String, String> entries) throws DuplicatedConversionValueException, IllegalKeyFormatException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        validateEntriesMap(entries);
        this.entries = entries;
    }

    public Map<String, String> getEntries() {
        return Collections.unmodifiableMap(entries);
    }

    public void setEntries(Map<String, String> entries) throws DuplicatedConversionValueException, IllegalKeyFormatException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        validateEntriesMap(entries);
        this.entries = entries;
    }

    public void addEntry(String key, String value) throws DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException, IllegalKeyFormatException {
        validateNewEntry(key, value);
        validateKeyFormat(key);
        entries.put(key, value);
    }
}
