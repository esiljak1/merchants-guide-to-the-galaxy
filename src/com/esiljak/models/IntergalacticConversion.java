package com.esiljak.models;

import com.esiljak.exceptions.*;
import com.esiljak.helpers.NumberParser;
import com.esiljak.helpers.StringParser;

import java.util.*;

public class IntergalacticConversion {
    private static final String DUPLICATED_KEY = "Cannot have multiple entries of the same key";
    private static final String DUPLICATED_VALUE = "Cannot have multiple entries of the same value";
    private static final String ILLEGAL_FORMAT = "Illegal key format detected";
    private static final String NAME_REPEAT = "Item with that name already exists";
    private static final String NO_ITEM_NAME = "No item name provided";
    private static final String UNKNOWN_QUANTITY = "Unknown quantity detected";
    private Map<String, String> entries;
    private List<SellingItem> sellingItems = new ArrayList<>();

    private void validateNewEntry(String key, String value) throws DuplicatedConversionKeyException, DuplicatedConversionValueException, IllegalRomanNumeralException {
        if (entries != null && entries.containsKey(key))
            throw new DuplicatedConversionKeyException(DUPLICATED_KEY);
        if (entries != null && entries.containsValue(value))
            throw new DuplicatedConversionValueException(DUPLICATED_VALUE);

        NumberParser.checkValidDigit(value);
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

    private String extractRomanNumber(List<String> quantityWithItemList) throws IllegalSellingItemFormatException {
        StringBuilder result = new StringBuilder();
        String itemName = quantityWithItemList.get(quantityWithItemList.size() - 1).trim();
        if (entries.containsKey(itemName))
            throw new IllegalSellingItemFormatException(NO_ITEM_NAME);

        for(int i = 0; i < quantityWithItemList.size() - 1; i++){
            String numberCode = quantityWithItemList.get(i).trim();
            if (!entries.containsKey(numberCode))
                throw new IllegalSellingItemFormatException(UNKNOWN_QUANTITY + ": " + numberCode);

            result.append(entries.get(numberCode));
        }

        return result.toString();
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

    public void addEntry(String sentence) throws IllegalKeyFormatException, DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        List<String> keyValueList = StringParser.getKeyValuePair(sentence);
        String key = keyValueList.get(0), value = keyValueList.get(1);

        validateKeyFormat(key);
        validateNewEntry(key, value);

        entries.put(key, value);
    }

    public SellingItem getSellingItem(String itemName){
        var list = sellingItems.stream().filter(sellingItem -> sellingItem.getName().equals(itemName)).toList();
        return list.size() == 0 ? null : list.get(0);
    }

    public void addSellingItem(String sentence) throws IllegalSellingItemFormatException, IllegalRomanNumeralException, IllegalItemNameException, IllegalQuantityException, IllegalPriceException {
        List<String> quantityWithItemList = StringParser.getQuantityWithItem(sentence);
        String romanNumber = extractRomanNumber(quantityWithItemList);
        String itemName = quantityWithItemList.get(quantityWithItemList.size() - 1).trim();
        RomanNumeral numeral = new RomanNumeral(romanNumber);

        float price = StringParser.getItemPrice(sentence);
        if (getSellingItem(itemName) != null)
            throw new IllegalSellingItemFormatException(NAME_REPEAT);

        sellingItems.add(new SellingItem(itemName, price, numeral.getValue()));
    }
}
