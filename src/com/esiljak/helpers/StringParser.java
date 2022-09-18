package com.esiljak.helpers;

import com.esiljak.exceptions.IllegalKeyFormatException;
import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;

import java.util.List;

public class StringParser {
    private static final String ILLEGAL_SENTENCE = "Illegal sentence format to parse";
    private static final String NO_CREDIT = "No amount of credits provided";
    private static final String INVALID_FORMAT = "Invalid format";
    private static final String NO_QUANTITY_OR_ITEM_NAME = "No quantity or item name provided";
    private static final String ILLEGAL_PRICE = "Illegal price found in sentence";
    private static final String QUANTITY_QUERY_START = "Quantity query has to start with \"how much is \"";
    private static final String PRICE_QUERY_START = "Price query has to start with \"how many credits is \"";
    private static final String ILLEGAL_QUERY = "Illegal query passed";

    private static void checkValidityOfArray(String[] array) throws IllegalKeyFormatException {
        if (array.length != 2)
            throw new IllegalKeyFormatException(ILLEGAL_SENTENCE);
    }

    private static void checkValidityOfSentence(String sentence) throws IllegalSellingItemFormatException {
        if (!sentence.toLowerCase().contains("credit"))
            throw new IllegalSellingItemFormatException(NO_CREDIT);
    }

    private static void checkValidityOfSellingItemArray(String[] array) throws IllegalSellingItemFormatException {
        if (array.length != 2)
            throw new IllegalSellingItemFormatException(INVALID_FORMAT);
    }

    private static void checkValidityOfQuantityList(List<String> list) throws IllegalSellingItemFormatException {
        if (list.size() < 2)
            throw new IllegalSellingItemFormatException(NO_QUANTITY_OR_ITEM_NAME);
    }

    private static float parsePrice(String priceString) throws IllegalSellingItemFormatException {
        float price;
        try {
            price = (float) Double.parseDouble(priceString);
        }catch (NumberFormatException e){
            throw new IllegalSellingItemFormatException(ILLEGAL_PRICE);
        }
        return price;
    }

    private static void checkValidityOfQuantityQuery(String query) throws IllegalQueryException {
        if (!query.toLowerCase().startsWith("how much is "))
            throw new IllegalQueryException(QUANTITY_QUERY_START);
    }

    private static void checkValidityOfPriceQuery(String query) throws IllegalQueryException {
        if (!query.toLowerCase().startsWith("how many credits is "))
            throw new IllegalQueryException(PRICE_QUERY_START);
    }

    private static void checkValidityOfQueryArray(String[] array) throws IllegalQueryException {
        if (array.length != 2 || array[1].split(" ").length == 0)
            throw new IllegalQueryException(ILLEGAL_QUERY);
    }

    private static String removeQuestionMark(String sentence){
        return sentence.replaceAll("\\?", "");
    }

    private static List<String> getQueryItems(String query) throws IllegalQueryException {
        String formattedQuery = removeQuestionMark(query);
        String[] stringArray = formattedQuery.split(" is ");
        checkValidityOfQueryArray(stringArray);
        return List.of(stringArray[1].split(" "));
    }

    public static List<String> getKeyValuePair(String sentence) throws IllegalKeyFormatException {
        String[] stringArray = sentence.split(" is ");
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
        checkValidityOfSellingItemArray(priceWithCurrency);

        return parsePrice(priceWithCurrency[0].trim());
    }

    public static List<String> getNumberCodeForQuantityQuery(String query) throws IllegalQueryException {
        checkValidityOfQuantityQuery(query);
        return getQueryItems(query);
    }

    public static List<String> getQuantityWithItemForPriceQuery(String query) throws IllegalQueryException {
        checkValidityOfPriceQuery(query);
        return getQueryItems(query);
    }
}
