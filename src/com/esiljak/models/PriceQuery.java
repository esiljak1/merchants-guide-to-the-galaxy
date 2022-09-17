package com.esiljak.models;

import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;
import com.esiljak.helpers.StringParser;

import java.util.Locale;
import java.util.Optional;

public class PriceQuery extends Query {
    private static final String UNKNOWN_ITEM = "Unknown item in the query";
    public PriceQuery(IntergalacticConversion conversion) {
        super(conversion);
    }

    private SellingItem getSellingItem(String itemName) throws IllegalQueryException {
        SellingItem item = getConversion().getSellingItem(itemName);
        if (item == null)
            throw new IllegalQueryException(UNKNOWN_ITEM);
        return item;
    }

    @Override
    public String queryAnswer(String query) throws IllegalQueryException, IllegalSellingItemFormatException, IllegalRomanNumeralException {
        var list = StringParser.getQuantityWithItemForPriceQuery(query);
        String romanNumber = getConversion().extractRomanNumber(list.subList(0, list.size() - 1));
        String itemName = list.get(list.size() - 1);

        SellingItem item = getSellingItem(itemName);
        RomanNumeral numeral = new RomanNumeral(romanNumber);

        String answer = list.stream().reduce((s1, s2) -> s1.trim() + " " + s2.trim()).get();

        return answer + " is " + String.format(Locale.ENGLISH, "%.2f", item.getPrice(numeral.getValue())) + " Credits";
    }
}
