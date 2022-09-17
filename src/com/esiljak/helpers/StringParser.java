package com.esiljak.helpers;

import com.esiljak.exceptions.IllegalKeyFormatException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;

import java.util.List;

public class StringParser {
    private static final String ILLEGAL_SENTENCE = "Illegal sentence format to parse";

    private static void checkValidityOfArray(String[] array) throws IllegalKeyFormatException {
        if (array.length != 2)
            throw new IllegalKeyFormatException(ILLEGAL_SENTENCE);
    }

    private static void checkValidityOfSentence(String sentence) throws IllegalSellingItemFormatException {
        if (!sentence.toLowerCase().contains("credit"))
            throw new IllegalSellingItemFormatException("No amount of credits provided");
    }

    private static void checkValidityOfSellingItemArray(String[] array) throws IllegalSellingItemFormatException {
        if (array.length != 2)
            throw new IllegalSellingItemFormatException("Invalid format");
    }

    private static void checkValidityOfQuantityList(List<String> list) throws IllegalSellingItemFormatException {
        if (list.size() < 2)
            throw new IllegalSellingItemFormatException("No quantity or item name provided");
    }

    private static void checkValidityOfPriceSentence(String[] array) throws IllegalSellingItemFormatException {
        if(array.length != 2)
            throw new IllegalSellingItemFormatException("Illegal or no price found in sentence");
    }

    public static List<String> getKeyValuePair(String sentence) throws IllegalKeyFormatException {
        String[] stringArray = sentence.split("is");
        checkValidityOfArray(stringArray);
        return List.of(stringArray[0].trim(), stringArray[1].trim());
    }

    public static List<String> getQuantityWithItem(String sentence) throws IllegalSellingItemFormatException {
        checkValidityOfSentence(sentence);
        String[] stringArray = sentence.split(" is ");
        checkValidityOfSellingItemArray(stringArray);

        List<String> quantityWithItemList = List.of(stringArray[0].split(" "));
        checkValidityOfQuantityList(quantityWithItemList);
        return quantityWithItemList;
    }

    public static float getItemPrice(String sentence) throws IllegalSellingItemFormatException {
        String[] stringArray = sentence.split(" is ");
        checkValidityOfSellingItemArray(stringArray);

        String[] priceWithCurrency = stringArray[1].split(" ");
        checkValidityOfPriceSentence(priceWithCurrency);

        return (float) Double.parseDouble(priceWithCurrency[0].trim());
    }
}
