package com.esiljak.models;

import com.esiljak.exceptions.*;
import com.esiljak.helpers.StringParser;
import com.esiljak.services.IntergalacticConversionService;

import java.util.*;

public class IntergalacticConversion {
    private final IntergalacticConversionService service = new IntergalacticConversionService(this);
    private Map<String, String> entries;
    private final List<SellingItem> sellingItems = new ArrayList<>();

    public IntergalacticConversion() {
        entries = new HashMap<>();
    }

    public IntergalacticConversion(Map<String, String> entries) throws DuplicatedConversionValueException, IllegalKeyFormatException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        service.validateEntriesMap(entries);
        this.entries = entries;
    }

    public Map<String, String> getEntries() {
        return entries != null ? Collections.unmodifiableMap(entries) : null;
    }

    public void setEntries(Map<String, String> entries) throws DuplicatedConversionValueException, IllegalKeyFormatException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        service.validateEntriesMap(entries);
        this.entries = entries;
    }

    public String extractRomanNumber(List<String> quantityList) throws IllegalSellingItemFormatException {
        service.checkQuantityList(quantityList);
        StringBuilder result = new StringBuilder();

        for (String s : quantityList) {
            String numberCode = s.trim();
            service.validateNumberCodeExists(numberCode);
            result.append(entries.get(numberCode));
        }

        return result.toString();
    }

    public void addEntry(String key, String value) throws DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException, IllegalKeyFormatException {
        service.validateNewEntry(key, value);
        service.validateKeyFormat(key);
        entries.put(key, value);
    }

    public void addEntry(String sentence) throws IllegalKeyFormatException, DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        List<String> keyValueList = StringParser.getKeyValuePair(sentence);
        String key = keyValueList.get(0), value = keyValueList.get(1);

        service.validateKeyFormat(key);
        service.validateNewEntry(key, value);

        entries.put(key, value);
    }

    public SellingItem getSellingItem(String itemName){
        var list = sellingItems.stream().filter(sellingItem -> sellingItem.getName().equals(itemName)).toList();
        return list.size() == 0 ? null : list.get(0);
    }

    public void addSellingItem(String sentence) throws IllegalSellingItemFormatException, IllegalRomanNumeralException, IllegalItemNameException, IllegalQuantityException, IllegalPriceException {
        List<String> quantityWithItemList = StringParser.getQuantityWithItem(sentence);
        String romanNumber = extractRomanNumber(quantityWithItemList.subList(0, quantityWithItemList.size() - 1));

        String itemName = quantityWithItemList.get(quantityWithItemList.size() - 1).trim();
        service.validateItemName(itemName);
        service.validateSellingItemNotExisting(itemName);

        RomanNumeral numeral = new RomanNumeral(romanNumber);

        float price = StringParser.getItemPrice(sentence);
        sellingItems.add(new SellingItem(itemName, price, numeral.getValue()));
    }

    public String query(String query) throws IllegalQueryException {
        String answer;
        try {
            answer = new QuantityQuery(this).queryAnswer(query);
            return answer;
        } catch (Exception ignored){}

        try {
            answer = new PriceQuery(this).queryAnswer(query);
        } catch (Exception ignored) {
            throw new IllegalQueryException("Invalid query format");
        }
        return answer;
    }
}
