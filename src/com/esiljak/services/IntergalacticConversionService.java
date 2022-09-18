package com.esiljak.services;

import com.esiljak.exceptions.*;
import com.esiljak.helpers.NumberParser;
import com.esiljak.models.IntergalacticConversion;

import java.util.Map;

public class IntergalacticConversionService {
    private static final String DUPLICATED_KEY = "Cannot have multiple entries of the same key";
    private static final String DUPLICATED_VALUE = "Cannot have multiple entries of the same value";
    private static final String ILLEGAL_FORMAT = "Illegal key format detected";
    private static final String NAME_REPEAT = "Item with that name already exists";
    private static final String NO_ITEM_NAME = "No item name provided";
    private static final String UNKNOWN_QUANTITY = "Unknown quantity detected";
    private final IntergalacticConversion conversion;

    public IntergalacticConversionService(IntergalacticConversion conversion) {
        this.conversion = conversion;
    }

    public void validateNewEntry(String key, String value) throws DuplicatedConversionKeyException, DuplicatedConversionValueException, IllegalRomanNumeralException {
        if (conversion.getEntries() != null && conversion.getEntries().containsKey(key))
            throw new DuplicatedConversionKeyException(DUPLICATED_KEY);
        if (conversion.getEntries() != null && conversion.getEntries().containsValue(value))
            throw new DuplicatedConversionValueException(DUPLICATED_VALUE);

        NumberParser.checkValidDigit(value);
    }

    public void validateKeyFormat(String key) throws IllegalKeyFormatException {
        if (key == null || key.trim().isEmpty() || key.split(" ").length != 1)
            throw new IllegalKeyFormatException(ILLEGAL_FORMAT);
    }

    public void validateEntriesMap(Map<String, String> map) throws DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException, IllegalKeyFormatException {
        if (map == null)
            throw new IllegalKeyFormatException("");
        for(Map.Entry<String, String> entry : map.entrySet()){
            validateNewEntry(entry.getKey(), entry.getValue());
            validateKeyFormat(entry.getKey());
        }
    }

    public void validateItemName(String itemName) throws IllegalSellingItemFormatException {
        if (conversion.getEntries().containsKey(itemName))
            throw new IllegalSellingItemFormatException(NO_ITEM_NAME);
    }

    public void validateSellingItemNotExisting(String itemName) throws IllegalSellingItemFormatException {
        if (conversion.getSellingItem(itemName) != null)
            throw new IllegalSellingItemFormatException(NAME_REPEAT);
    }

    public void validateNumberCodeExists(String numberCode) throws IllegalSellingItemFormatException {
        if (!conversion.getEntries().containsKey(numberCode))
            throw new IllegalSellingItemFormatException(UNKNOWN_QUANTITY + ": " + numberCode);
    }
}
